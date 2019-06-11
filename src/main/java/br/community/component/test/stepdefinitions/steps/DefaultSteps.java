package br.community.component.test.stepdefinitions.steps;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.community.component.test.domains.TargetRequest;
import br.community.component.test.domains.exceptions.FeignException;
import br.community.component.test.gateways.FileGateway;
import br.community.component.test.gateways.stubby.jsons.StubbyResponse;
import br.community.component.test.usecases.CreateStubbyUsecase;
import br.community.component.test.usecases.GetStubbyUsecase;
import br.community.component.test.usecases.RequestTargetUseCase;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DefaultSteps {

  private TargetRequest request;
  private ResponseEntity response;
  private String scenarioName;

  private final FileGateway fileGateway;
  private final RequestTargetUseCase requestTargetUseCase;
  private final CreateStubbyUsecase createStubbyUsecase;
  private final GetStubbyUsecase getStubbyUsecase;
  private FeignException httpException;
  private Map<String, Integer> stubbyIdMap;

  @Autowired
  public DefaultSteps(
      FileGateway fileGateway,
      RequestTargetUseCase requestTargetUseCase,
      CreateStubbyUsecase createStubbyUsecase,
      GetStubbyUsecase getStubbyUsecase) {
    this.fileGateway = fileGateway;
    this.requestTargetUseCase = requestTargetUseCase;
    this.createStubbyUsecase = createStubbyUsecase;
    this.getStubbyUsecase = getStubbyUsecase;
    request = new TargetRequest<>();
    stubbyIdMap = new HashMap<>();
    httpException = null;
  }

  @Before
  public void before(Scenario scenario) {
    scenarioName = FilenameUtils.getBaseName(scenario.getUri());
  }

  @Given("I have a request with body ([^\"]*)")
  public void iHaveARequestWith(String requestPayload) throws IOException {
    Object body = fileGateway.getObjectFromFile(scenarioName, requestPayload, Object.class);
    request.setBody(body);
  }

  @When("I make a ([^\"]*) to ([^\"]*)")
  public void iMakeATo(String method, String uri) {
    request.setMethod(method);
    request.setUri(uri);
    response = null;
    httpException = null;

    try {
      response = requestTargetUseCase.execute(request);
    } catch (FeignException e) {
      httpException = e;
    }
  }

  @Then("I expect ([^\"]*) as response")
  public void iExpectAsResponse(String responsePayload) throws IOException, JSONException {
    String responseExpected = fileGateway.getJsonStringFromFile(scenarioName, responsePayload);
    String responseReceived = fileGateway.getJsonStringFromObject(response.getBody());
    JSONAssert.assertEquals(responseExpected, responseReceived, true);
  }

  @Then("A have a mock ([^\"]*) for dependency ([^\"]*)")
  public void aHaveAMockForDependency(String mockName, String serviceName) throws IOException {
    Integer stubbyId = createStubbyUsecase.execute(scenarioName, serviceName, mockName);
    stubbyIdMap.put(getStubbyKey(scenarioName, serviceName, mockName), stubbyId);
  }

  @Then("I expect mock ([^\"]*) for dependency ([^\"]*) to have been called (\\d+) times")
  public void iExpectMockForDependencyToHaveBeenCalledTimes(
      String mockName, String serviceName, int times) {
    String mapKey = getStubbyKey(scenarioName, serviceName, mockName);
    Integer stubbyId = stubbyIdMap.get(mapKey);

    StubbyResponse stubby = getStubbyUsecase.execute(stubbyId);
    assertThat(stubby.getHits(), equalTo(times));
  }

  @Given("I make a request defined in ([^\"]*)")
  public void iHaveARequestDefinedIn(String requestSpecFilePath) throws IOException {
    request = fileGateway.getObjectFromFile(scenarioName, requestSpecFilePath, TargetRequest.class);
    response = null;
    httpException = null;

    try {
      response = requestTargetUseCase.execute(request);
    } catch (FeignException e) {
      httpException = e;
    }
  }

  @Then("I expect to receive a (\\d+) status")
  public void iExpectToReceiveAStatus(int httpStatusExpected) {
    int httpStatusReceived;
    if (response == null) {
      httpStatusReceived = httpException.getResponse().status();
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

    String responseExpected = fileGateway.getJsonStringFromFile(scenarioName, responseBodyExpected);
    if (response == null) {
      httpStatusReceived = httpException.getResponse().status();
      responseReceived = new ObjectMapper().writeValueAsString(httpException.getResponse().body());
    } else {
      httpStatusReceived = response.getStatusCode().value();
      responseReceived = fileGateway.getJsonStringFromObject(response.getBody());
    }
    assertThat(httpStatusReceived, equalTo(httpStatusExpected));
    JSONAssert.assertEquals(responseExpected, responseReceived, true);
  }

  private String getStubbyKey(String scenario, String serviceName, String mockName) {
    return scenario + serviceName + mockName;
  }
}
