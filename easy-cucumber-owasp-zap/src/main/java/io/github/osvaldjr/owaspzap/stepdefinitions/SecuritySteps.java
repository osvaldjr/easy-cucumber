package io.github.osvaldjr.owaspzap.stepdefinitions;

import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.zaproxy.clientapi.core.Alert;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ApiResponseList;
import org.zaproxy.clientapi.core.ApiResponseSet;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.osvaldjr.core.objects.properties.ApplicationProperties;
import io.github.osvaldjr.core.stepdefinitions.Steps;
import io.github.osvaldjr.owaspzap.objects.AlertRisk;
import io.github.osvaldjr.owaspzap.objects.DataType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
public class SecuritySteps extends Steps {

  private final ClientApi zapProxyApi;
  private final ApplicationProperties applicationProperties;

  private String policyName;

  @Autowired
  public SecuritySteps(ClientApi zapProxyApi, ApplicationProperties applicationProperties) {
    this.zapProxyApi = zapProxyApi;
    this.applicationProperties = applicationProperties;
  }

  @Given("^I import context from open API specification \"([^\"]*)\"$")
  public void iImportContextFromOpenAPISpecification(String path)
      throws ClientApiException, InterruptedException {
    String url = getTargetUrl() + path;
    log.info("Import Open API from url: " + url);
    zapProxyApi.openapi.importUrl(url, null);

    waitPassiveScanRunning();
    verifyThatTheProxyHasCapturedHostInformation();
  }

  @Given("^I exclude urls from scan$")
  public void iExcludeUrlsFromScan(List<DataType> data) {
    data.forEach(
        type -> {
          try {
            zapProxyApi.ascan.excludeFromScan(type.getUrl());
          } catch (ClientApiException e) {
            fail(e.getMessage());
          }
        });
  }

  @Given("^I generate the proxy session$")
  public void iGenerateTheProxySession() throws ClientApiException {
    String sessionFolder = getDataFolder("security-session");
    log.info("Generate session in folder " + sessionFolder);
    deleteAndCreateFolder(sessionFolder);

    zapProxyApi.core.setHomeDirectory(sessionFolder);
    zapProxyApi.core.saveSession(String.format("%s/%s", sessionFolder, "zap.log.session"), null);
  }

  @Given("^I generate security test HTML report with name \"([^\"]*)\"$")
  public void iGenerateSecurityTestHTMLReportWithName(String name)
      throws ClientApiException, IOException {
    byte[] report = zapProxyApi.core.htmlreport();

    String reportFolder = pathTarget() + "security-reports";
    log.info("Generate reports in folder " + reportFolder);
    new File(reportFolder).mkdirs();

    File htmlReport = new File(reportFolder, name + ".html");
    if (!htmlReport.createNewFile()) {
      log.warn("Occurred error in generate security test HTML report");
    }

    try (OutputStream htmlFile = new FileOutputStream(htmlReport.getAbsoluteFile())) {
      htmlFile.write(report);
    }
  }

  @Given("^I remove all alerts$")
  public void iRemoveScanAlerts() throws ClientApiException {
    zapProxyApi.alert.deleteAllAlerts();
  }

  @When("^I remove alerts$")
  public void iRemoveAlert(List<DataType> data) throws ClientApiException {
    List<Alert> alerts = zapProxyApi.getAlerts(null, -1, -1);
    List<Alert> alertsExclude =
        alerts.stream()
            .filter(alert -> data.stream().anyMatch(type -> isExclude(alert, type)))
            .collect(Collectors.toList());

    alertsExclude.forEach(
        alert -> {
          try {
            zapProxyApi.alert.deleteAlert(alert.getId());
          } catch (ClientApiException e) {
            fail(e.getMessage());
          }
        });
  }

  @When("^I import scan policy \"([^\"]*)\" from file \"([^\"]*)\"$")
  public void iImportScanPolicyFromFile(String policyName, String policy)
      throws ClientApiException {
    this.policyName = policyName;

    ApiResponseList policies = (ApiResponseList) zapProxyApi.ascan.scanPolicyNames();
    if (policies.getItems().stream()
        .anyMatch(item -> ((ApiResponseElement) item).getValue().equals(policyName))) {
      zapProxyApi.ascan.removeScanPolicy(policyName);
    }

    String resource = "policy/" + policy;
    String path = pathResource(resource);
    String dataFolder = applicationProperties.getDependencies().getOwasp().getOverwriteDataFolder();
    if (StringUtils.isNotEmpty(dataFolder)) {
      path = dataFolder + resource;
    }
    log.info("Import policy from path " + path);
    zapProxyApi.ascan.importScanPolicy(path);
  }

  @When("^I run active scan$")
  public void iRunActiveScan() throws ClientApiException, InterruptedException {
    zapProxyApi.ascan.scan(getTargetUrl(), "true", "false", policyName, null, null);

    while (ascanStatus()) {
      log.info("Running active scan");
      Thread.sleep(10000);
    }
    log.info("Finish active scan");
  }

  @Then("^the number of risks per category should not be greater than$")
  public void theNumberOfRisksPerCategoryShouldNotBeGreaterThan(List<AlertRisk> risks)
      throws ClientApiException {
    List<Alert> alerts = zapProxyApi.getAlerts(null, -1, -1);

    List<Alert> lowAlerts = getAlertsWithRisk(alerts, Alert.Risk.Low);
    log.info("Found {} low risk alerts", lowAlerts.size());
    List<Alert> mediumAlerts = getAlertsWithRisk(alerts, Alert.Risk.Medium);
    log.info("Found {} medium risk alerts", mediumAlerts.size());
    List<Alert> highAlerts = getAlertsWithRisk(alerts, Alert.Risk.High);
    log.info("Found {} high risk alerts", highAlerts.size());
    List<Alert> informationalAlerts = getAlertsWithRisk(alerts, Alert.Risk.Informational);
    log.info("Found {} informational risk alerts", informationalAlerts.size());

    AlertRisk alertRisk = risks.get(0);
    assertThat(lowAlerts.size(), lessThanOrEqualTo(alertRisk.getLow()));
    assertThat(mediumAlerts.size(), lessThanOrEqualTo(alertRisk.getMedium()));
    assertThat(highAlerts.size(), lessThanOrEqualTo(alertRisk.getHigh()));
    assertThat(informationalAlerts.size(), lessThanOrEqualTo(alertRisk.getInformational()));
  }

  private void deleteAndCreateFolder(String path) {
    try {
      File file = new File(path);
      if (file.exists()) {
        FileUtils.deleteDirectory(file);
      }
      file.mkdirs();
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  private String pathResource(String resource) {
    return Thread.currentThread().getContextClassLoader().getResource(resource).getPath();
  }

  private String pathTarget() {
    String target = "/target/";
    return StringUtils.substringBefore(pathResource(StringUtils.EMPTY), target) + target;
  }

  private boolean ascanStatus() throws ClientApiException {
    ApiResponseList scans = (ApiResponseList) zapProxyApi.ascan.scans();
    ApiResponseSet items = (ApiResponseSet) scans.getItems().get(0);
    return StringUtils.equals(items.getStringValue("state"), "RUNNING");
  }

  private List<Alert> getAlertsWithRisk(List<Alert> alertsList, Alert.Risk risk) {
    return alertsList.stream()
        .filter(
            alert -> {
              boolean returned = alert.getRisk().equals(risk);
              if (returned) {
                log.info(
                    "Vulnerability alert from risk: \"{}\", id: \"{}\", name: \"{}\", other: \"{}\" and url: \"{}\"",
                    risk.toString(),
                    alert.getId(),
                    alert.getName(),
                    StringUtils.isEmpty(alert.getOther())
                        ? Strings.EMPTY
                        : StringUtils.substring(alert.getOther(), 0, 200),
                    alert.getUrl());
              }
              return returned;
            })
        .collect(Collectors.toList());
  }

  private boolean isExclude(Alert alert, DataType type) {
    Boolean exclude = null;
    if (StringUtils.isNotEmpty(type.getName())) {
      exclude = alert.getName().matches(type.getName());
    }
    if (StringUtils.isNotEmpty(type.getUrl())) {
      if (exclude == null) {
        exclude = alert.getUrl().matches(type.getUrl());
      } else {
        exclude &= alert.getUrl().matches(type.getUrl());
      }
    }
    return exclude;
  }

  private String getTargetUrl() {
    String url = null;
    try {
      url =
          applicationProperties
              .getTarget()
              .getUrl()
              .replace("localhost", InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      log.error("Cannot get local ip", e);
      fail(e.getMessage());
    }
    return url;
  }

  private String getDataFolder(String name) {
    String overwriteDataFolder =
        applicationProperties.getDependencies().getOwasp().getOverwriteDataFolder();
    String sessionFolder = pathTarget() + name;
    if (StringUtils.isNotEmpty(overwriteDataFolder)) {
      sessionFolder = overwriteDataFolder + name;
    }
    return sessionFolder;
  }

  private void waitPassiveScanRunning() throws InterruptedException, ClientApiException {
    while (!"0".equals(zapProxyApi.pscan.recordsToScan().toString())) {
      log.info("Running passive scan");
      Thread.sleep(1000);
    }
    log.info("Finish record passive scan");
  }

  private void verifyThatTheProxyHasCapturedHostInformation() throws ClientApiException {
    ApiResponseList hosts = (ApiResponseList) zapProxyApi.core.hosts();

    assertTrue(
        hosts.getItems().stream().anyMatch(host -> getTargetUrl().contains(host.toString())));
  }
}
