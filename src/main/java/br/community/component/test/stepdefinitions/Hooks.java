package br.community.component.test.stepdefinitions;

import org.springframework.beans.factory.annotation.Autowired;

import br.community.component.test.gateways.FeatureGateway;
import br.community.component.test.gateways.stubby.StubbyGateway;
import cucumber.api.java.Before;

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
