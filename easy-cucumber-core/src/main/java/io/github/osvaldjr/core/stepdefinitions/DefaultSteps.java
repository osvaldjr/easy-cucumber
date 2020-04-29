package io.github.osvaldjr.core.stepdefinitions;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.osvaldjr.core.objects.FileVariable;
import io.github.osvaldjr.core.objects.TargetRequest;
import io.github.osvaldjr.core.objects.exceptions.FeignException;
import io.github.osvaldjr.core.utils.FileUtils;
import io.github.osvaldjr.core.utils.RequestHttpTarget;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultSteps extends Steps {

  private final FileUtils fileUtils;
  private final RequestHttpTarget requestHttpTarget;

  private TargetRequest request;
  private ResponseEntity response;
  private FeignException httpException;

  @Autowired
  public DefaultSteps(FileUtils fileUtils, RequestHttpTarget requestHttpTarget) {
    this.fileUtils = fileUtils;
    this.requestHttpTarget = requestHttpTarget;
    request = new TargetRequest<>();
    httpException = null;
  }

  @Before
  public void before(Scenario scenario) {
    scenarioName = FilenameUtils.getBaseName(scenario.getUri());
  }

  @Before("@CleanFileVariable")
  public void cleanupFileVariable() {
    FileVariable.clear();
  }

  @Given("I have a request with body ([^\"]*)")
  public void iHaveARequestWith(String requestPayload) throws IOException {
    Object body = fileUtils.getObjectFromFile(scenarioName, requestPayload, Object.class);
    request.setBody(body);
  }

  @When("I make a ([^\"]*) to ([^\"]*)")
  public void iMakeATo(String method, String uri) {
    request.setMethod(method);
    request.setUrl(uri);
    response = null;
    httpException = null;

    try {
      response = requestHttpTarget.execute(request);
    } catch (FeignException e) {
      httpException = e;
    }
  }

  @Then("I expect ([^\"]*) as response")
  public void iExpectAsResponse(String responsePayload) throws IOException, JSONException {
    String responseExpected = fileUtils.getJsonStringFromFile(scenarioName, responsePayload);
    String responseReceived = fileUtils.getJsonStringFromObject(response.getBody());
    JSONAssert.assertEquals(responseExpected, responseReceived, true);
  }

  @Given("I make a request defined in ([^\"]*)")
  public void iHaveARequestDefinedIn(String requestSpecFilePath) throws IOException {
    request = fileUtils.getObjectFromFile(scenarioName, requestSpecFilePath, TargetRequest.class);
    response = null;
    httpException = null;

    try {
      response = requestHttpTarget.execute(request);
    } catch (FeignException e) {
      httpException = e;
    }
  }

  @Then("I expect to receive a (\\d+) status")
  public void iExpectToReceiveAStatus(int httpStatusExpected) {
    int httpStatusReceived;
    if (response == null) {
      httpStatusReceived = httpException.getResponse().getStatus();
    } else {
      httpStatusReceived = response.getStatusCode().value();
    }
    assertThat(httpStatusReceived, equalTo(httpStatusExpected));
  }

  @Then("I expect to receive a (\\d+) status with body ([^\"]*)")
  public void iExpectToReceiveAWithBody(int httpStatusExpected, String responseBodyExpected)
      throws IOException, JSONException {
    int httpStatusReceived;
    String responseReceived;

    String responseExpected = fileUtils.getJsonStringFromFile(scenarioName, responseBodyExpected);
    if (response == null) {
      httpStatusReceived = httpException.getResponse().getStatus();
      responseReceived = httpException.getResponse().getJsonBody();
    } else {
      httpStatusReceived = response.getStatusCode().value();
      responseReceived = fileUtils.getJsonStringFromObject(response.getBody());
    }
    assertThat(httpStatusReceived, equalTo(httpStatusExpected));
    JSONAssert.assertEquals(responseExpected, responseReceived, false);
  }

  @Given("my application host is ([^\"]*)")
  public void myApplicationHostIs(String host) {
    request.setHost(host);
  }

  @Then("response contains property ([^\"]*) with value ([^\"]*)")
  public void responseContainsPropertyWithValue(String jsonPath, String value) {
    DocumentContext documentContext = JsonPath.parse(response.getBody());
    String jsonPathValue = documentContext.read(jsonPath).toString();
    assertEquals(jsonPathValue, value);
  }

  @Then("response does not contain property ([^\"]*)")
  public void responseDoesNotContainProperty(String jsonPath) {
    boolean pathNotFound = false;
    DocumentContext documentContext = JsonPath.parse(response.getBody());
    try {
      documentContext.read(jsonPath);
    } catch (PathNotFoundException e) {
      pathNotFound = true;
    }
    Assert.assertTrue(pathNotFound);
  }

  @Then("response is valid according to schema ([^\"]*)")
  public void responseIsValidAccordingToSchema(String schemaPath)
      throws FileNotFoundException, JsonProcessingException, JSONException {
    String responseExpected = fileUtils.getJsonStringFromFile(scenarioName, schemaPath);
    String responseReceived = fileUtils.getJsonStringFromObject(response.getBody());

    JSONObject jsonSchema = new JSONObject(new JSONTokener(responseExpected));
    JSONObject jsonSubject = new JSONObject(new JSONTokener(responseReceived));

    Schema schema = SchemaLoader.load(jsonSchema);
    schema.validate(jsonSubject);
  }

  public ResponseEntity getResponse() {
    return response;
  }
}
