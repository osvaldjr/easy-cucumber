package io.github.osvaldjr.stepdefinitions.steps;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.Before;
import io.github.osvaldjr.domains.properties.QueueProperties;
import io.github.osvaldjr.gateways.FeatureGateway;
import io.github.osvaldjr.gateways.stubby.StubbyGateway;
import io.github.osvaldjr.usecases.CleanQueueUseCase;

public class Hooks {

  private final StubbyGateway stubbyGateway;
  private final FeatureGateway featureGateway;
  private final CleanQueueUseCase cleanQueueUseCase;
  private final QueueProperties queueProperties;

  @Autowired
  public Hooks(
      StubbyGateway stubbyGateway,
      FeatureGateway featureGateway,
      CleanQueueUseCase cleanQueueUseCase,
      QueueProperties queueProperties) {
    this.stubbyGateway = stubbyGateway;
    this.featureGateway = featureGateway;
    this.cleanQueueUseCase = cleanQueueUseCase;
    this.queueProperties = queueProperties;
  }

  @Before("@CleanStubby")
  public void cleanupStubby() {
    stubbyGateway.deleteAllServices();
  }

  @Before("@EnableFeatures")
  public void enableFeatures() {
    featureGateway.enableAllFeatures();
  }

  @Before("@DisableFeatures")
  public void disableFeatures() {
    featureGateway.disableAllFeatures();
  }

  @Before("@CleanQueues")
  public void cleanupQueues() {
    queueProperties.getNames().forEach(name -> cleanQueueUseCase.execute(name));
  }
}
