package component.test.stepdefinitions;

import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import component.test.ApplicationConfiguration;
import component.test.gateways.file.FileGateway;
import component.test.usecases.StubbyUsecase;
import component.test.usecases.TargetUseCase;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
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
  @Autowired private StubbyUsecase stubbyUsecase;

  @Given("I have a request with {string}")
  public void iHaveARequestWith(String requestPayload) throws Throwable {
    request = fileGateway.getObjectFromFile(scenarioName, requestPayload, Object.class);
  }

  @When("I make a {string} to {string}")
  public void iMakeATo(String method, String uri) {
    response = target.request(method, uri, request);
  }

  @Then("I expect {string} as response")
  public void iExpectAsResponse(String responsePayload) throws Throwable {
    String responseExpected = fileGateway.getJsonStringFromFile(scenarioName, responsePayload);
    String responseReceived = fileGateway.getJsonStringFromObject(response);
    JSONAssert.assertEquals(responseExpected, responseReceived, true);
  }

  @And("A have a mock {string} for {string}")
  public void aHaveAMockFor(String mockName, String serviceName) throws IOException {
    stubbyUsecase.execute(scenarioName, serviceName, mockName);
  }

  @Then("I expect mock {string} for {string} to have been called {int} times")
  public void iExpectMockForToHaveBeenCalledTimes(String mockName, String serviceName, int times) {
    System.out.println(mockName + " | " + serviceName + " | " + times);
    Integer mockHits = stubbyUsecase.getMockHits(scenarioName, serviceName, mockName);
    Assert.assertThat(mockHits, equalTo(times));
  }

  @Before
  public void before(Scenario scenario) {
    scenarioName = FilenameUtils.getBaseName(scenario.getUri());
  }
}
