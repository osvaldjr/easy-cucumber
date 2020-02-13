package io.github.osvaldjr.stepdefinitions.steps;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.Before;
import io.github.osvaldjr.objects.properties.FeaturesProperties;
import io.github.osvaldjr.gateways.FeatureGateway;
import io.github.osvaldjr.gateways.mock.MockGateway;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Hooks {

  private final MockGateway mockGateway;
  private final FeatureGateway featureGateway;
  private final FeaturesProperties featuresProperties;

  @Autowired
  public Hooks(
      MockGateway mockGateway,
      FeatureGateway featureGateway,
      FeaturesProperties featuresProperties) {
    this.mockGateway = mockGateway;
    this.featureGateway = featureGateway;
    this.featuresProperties = featuresProperties;
  }

  @Before("@CleanStubby")
  public void cleanupStubby() {
    mockGateway.deleteAllServices();
  }

  @Before("@EnableFeatures")
  public void enableFeatures() {
    Optional.ofNullable(featuresProperties.getNames()).orElseGet(ArrayList::new).stream()
        .forEach(featureGateway::enable);
  }

  @Before("@DisableFeatures")
  public void disableFeatures() {
    Optional.ofNullable(featuresProperties.getNames()).orElseGet(ArrayList::new).stream()
        .forEach(featureGateway::disable);
  }
}
