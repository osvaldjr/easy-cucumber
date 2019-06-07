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
import org.springframework.web.client.HttpStatusCodeException;

import br.community.component.test.domains.TargetRequest;
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

  private TargetRequest<Object> request;
  private ResponseEntity response;
  private String scenarioName;

  private final FileGateway fileGateway;
  private final RequestTargetUseCase requestTargetUseCase;
  private final CreateStubbyUsecase createStubbyUsecase;
  private final GetStubbyUsecase getStubbyUsecase;
  private HttpStatusCodeException httpException;
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

    try {
      response = requestTargetUseCase.execute(request);
    } catch (HttpStatusCodeException e) {
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

  @Then("I expect mock ([^\"]*) for ([^\"]*) to have been called (\\d+) times")
  public void iExpectMockForToHaveBeenCalledTimes(String mockName, String serviceName, int times) {
    String mapKey = getStubbyKey(scenarioName, serviceName, mockName);
    Integer stubbyId = stubbyIdMap.get(mapKey);

    StubbyResponse stubby = getStubbyUsecase.execute(stubbyId);
    assertThat(stubby.getHits(), equalTo(times));
  }

  private String getStubbyKey(String scenario, String serviceName, String mockName) {
    return scenario + serviceName + mockName;
  }
}
