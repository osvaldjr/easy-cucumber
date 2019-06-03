package br.community.component.test.stepdefinitions;

import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import br.community.component.test.domains.TargetRequest;
import br.community.component.test.gateways.FileGateway;
import br.community.component.test.usecases.StubbyUsecase;
import br.community.component.test.usecases.TargetUseCase;
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
  private final TargetUseCase target;
  private final StubbyUsecase stubbyUsecase;

  @Autowired
  public DefaultSteps(FileGateway fileGateway, TargetUseCase target, StubbyUsecase stubbyUsecase) {
    this.fileGateway = fileGateway;
    this.target = target;
    this.stubbyUsecase = stubbyUsecase;
    this.request = new TargetRequest<>();
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
    response = target.request(request);
  }

  @Then("I expect ([^\"]*) as response")
  public void iExpectAsResponse(String responsePayload) throws IOException, JSONException {
    String responseExpected = fileGateway.getJsonStringFromFile(scenarioName, responsePayload);
    String responseReceived = fileGateway.getJsonStringFromObject(response.getBody());
    JSONAssert.assertEquals(responseExpected, responseReceived, true);
  }

  @Then("A have a mock ([^\"]*) for ([^\"]*)")
  public void aHaveAMockFor(String mockName, String serviceName) throws IOException {
    stubbyUsecase.execute(scenarioName, serviceName, mockName);
  }

  @Then("I expect mock ([^\"]*) for ([^\"]*) to have been called (\\d+) times")
  public void iExpectMockForToHaveBeenCalledTimes(String mockName, String serviceName, int times) {
    Integer mockHits = stubbyUsecase.getMockHits(scenarioName, serviceName, mockName);
    Assert.assertThat(mockHits, equalTo(times));
  }
}
