package io.github.osvaldjr.stepdefinitions.steps;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.github.osvaldjr.gateways.mock.MockGateway;
import io.github.osvaldjr.utils.CreateStubbyUseCase;
import io.github.osvaldjr.utils.GetMockHitsUseCase;

public class DefaultSteps extends Steps {

  private String scenarioName;

  private final CreateStubbyUseCase createStubbyUsecase;
  private final GetMockHitsUseCase getMockHitsUsecase;
  private Map<String, Object> stubbyIdMap;
  private final MockGateway mockGateway;

  @Autowired
  public DefaultSteps(
      CreateStubbyUseCase createStubbyUsecase,
      GetMockHitsUseCase getMockHitsUsecase,
      MockGateway mockGateway) {
    this.createStubbyUsecase = createStubbyUsecase;
    this.getMockHitsUsecase = getMockHitsUsecase;
    this.mockGateway = mockGateway;
    stubbyIdMap = new HashMap<>();
  }

  @Before
  public void before(Scenario scenario) {
    scenarioName = FilenameUtils.getBaseName(scenario.getUri());
  }

  @Then("I have a mock ([^\"]*) for dependency ([^\"]*)")
  public void aHaveAMockForDependency(String mockName, String serviceName) throws IOException {
    Object stubbyId = createStubbyUsecase.execute(scenarioName, serviceName, mockName);
    stubbyIdMap.put(getStubbyKey(scenarioName, serviceName, mockName), stubbyId);
  }

  @Then("I expect mock ([^\"]*) for dependency ([^\"]*) to have been called (\\d+) times")
  public void iExpectMockForDependencyToHaveBeenCalledTimes(
      String mockName, String serviceName, Integer times) {
    String mapKey = getStubbyKey(scenarioName, serviceName, mockName);
    Object stubbyId = stubbyIdMap.get(mapKey);

    assertThat(getMockHitsUsecase.execute(stubbyId), equalTo(times));
  }

  @Given("I clear all mocks")
  public void iClearAllMocks() {
    mockGateway.deleteAllServices();
  }

  private String getStubbyKey(String scenario, String serviceName, String mockName) {
    return scenario + serviceName + mockName;
  }
}
