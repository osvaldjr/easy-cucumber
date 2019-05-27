package component.test.stepdefinitions;

import org.apache.commons.io.FilenameUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import component.test.ApplicationConfiguration;
import component.test.gateway.FileGateway;
import component.test.usecase.TargetUseCase;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@SpringBootTest(classes = ApplicationConfiguration.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class DefaultSteps {
  private Object request;
  private Object response;
  private String scenarioName;
  @Autowired private FileGateway fileGateway;
  @Autowired private TargetUseCase target;

  @Given("^I have a request with \"([^\"]*)\"$")
  public void i_have_a_request_with(String requestPayload) throws Throwable {
    request = fileGateway.getObjectFromFile(scenarioName, requestPayload);
  }

  @When("^I make a \"([^\"]*)\" to \"([^\"]*)\"$")
  public void i_make_a_to(String method, String uri) {
    response = target.request(method, uri, request);
  }

  @Then("^I expect \"([^\"]*)\" as response$")
  public void i_expect_as_response(String responsePayload) throws Throwable {
    String responseExpected = fileGateway.getJsonStringFromFile(scenarioName, responsePayload);
    String responseReceived = fileGateway.getJsonStringFromObject(response);
    JSONAssert.assertEquals(responseExpected, responseReceived, true);
  }

  @Before
  public void before(Scenario scenario) {
    scenarioName = FilenameUtils.getBaseName(scenario.getUri());
  }
}
