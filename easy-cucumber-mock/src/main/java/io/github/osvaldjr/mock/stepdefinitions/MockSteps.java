package io.github.osvaldjr.mock.stepdefinitions;

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
import io.github.osvaldjr.core.stepdefinitions.Steps;
import io.github.osvaldjr.core.objects.FileVariable;
import io.github.osvaldjr.mock.utils.CreateStubby;
import io.github.osvaldjr.mock.utils.GetMockHits;
import io.github.osvaldjr.mock.utils.Mock;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MockSteps extends Steps {

  private final CreateStubby createStubbyUsecase;
  private final GetMockHits getMockHitsUsecase;
  private final Mock mock;

  private Map<String, Object> stubbyIdMap = new HashMap<>();

  @Autowired
  public MockSteps(CreateStubby createStubbyUsecase, GetMockHits getMockHitsUsecase, Mock mock) {
    this.createStubbyUsecase = createStubbyUsecase;
    this.getMockHitsUsecase = getMockHitsUsecase;
    this.mock = mock;
  }

  @Before
  public void before(Scenario scenario) {
    scenarioName = FilenameUtils.getBaseName(scenario.getUri());
  }

  @Before("@CleanStubby")
  public void cleanupStubby() {
    mock.deleteAllServices();
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

    Integer mockHits = 0;
    if(stubbyIdMap.containsKey(mapKey)) {
      Object stubbyId = stubbyIdMap.get(mapKey);
      mockHits = getMockHitsUsecase.execute(stubbyId);
    }
    assertThat(mockHits, equalTo(times));
  }

  @Given("I clear all mocks")
  public void iClearAllMocks() {
    mock.deleteAllServices();
  }

  private String getStubbyKey(String scenario, String serviceName, String mockName) {
    return scenario + serviceName + mockName;
  }
}
