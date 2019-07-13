package io.github.osvaldjr.stepdefinitions.steps;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.Before;
import io.github.osvaldjr.domains.properties.FeaturesProperties;
import io.github.osvaldjr.domains.properties.QueueProperties;
import io.github.osvaldjr.gateways.FeatureGateway;
import io.github.osvaldjr.gateways.stubby.StubbyGateway;
import io.github.osvaldjr.usecases.CleanQueueUseCase;

public class Hooks {

  private final StubbyGateway stubbyGateway;
  private final FeatureGateway featureGateway;
  private final CleanQueueUseCase cleanQueueUseCase;
  private final QueueProperties queueProperties;
  private final FeaturesProperties featuresProperties;

  @Autowired
  public Hooks(
      StubbyGateway stubbyGateway,
      FeatureGateway featureGateway,
      CleanQueueUseCase cleanQueueUseCase,
      QueueProperties queueProperties,
      FeaturesProperties featuresProperties) {
    this.stubbyGateway = stubbyGateway;
    this.featureGateway = featureGateway;
    this.cleanQueueUseCase = cleanQueueUseCase;
    this.queueProperties = queueProperties;
    this.featuresProperties = featuresProperties;
  }

  @Before("@CleanStubby")
  public void cleanupStubby() {
    stubbyGateway.deleteAllServices();
  }

  @Before("@EnableFeatures")
  public void enableFeatures() {
    Optional.ofNullable(featuresProperties.getNames())
        .orElseGet(ArrayList::new)
        .stream()
        .forEach(featureGateway::enable);
  }

  @Before("@DisableFeatures")
  public void disableFeatures() {
    Optional.ofNullable(featuresProperties.getNames())
        .orElseGet(ArrayList::new)
        .stream()
        .forEach(featureGateway::disable);
  }

  @Before("@CleanQueues")
  public void cleanupQueues() {
    queueProperties.getNames().forEach(cleanQueueUseCase::execute);
  }
}
