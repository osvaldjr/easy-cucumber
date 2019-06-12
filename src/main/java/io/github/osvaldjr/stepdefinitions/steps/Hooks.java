package io.github.osvaldjr.stepdefinitions.steps;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.Before;
import io.github.osvaldjr.gateways.FeatureGateway;
import io.github.osvaldjr.gateways.stubby.StubbyGateway;

public class Hooks {

  private final StubbyGateway stubbyGateway;
  private final FeatureGateway featureGateway;

  @Autowired
  public Hooks(StubbyGateway stubbyGateway, FeatureGateway featureGateway) {
    this.stubbyGateway = stubbyGateway;
    this.featureGateway = featureGateway;
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
}
