package io.github.osvaldjr.stepdefinitions.steps;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Given;
import io.github.osvaldjr.domains.Feature;
import io.github.osvaldjr.domains.FeatureStatus;
import io.github.osvaldjr.domains.properties.FeaturesProperties;
import io.github.osvaldjr.gateways.FeatureGateway;

public class FeatureSteps extends Steps {

  private final FeatureGateway featureGateway;
  private final FeaturesProperties featuresProperties;

  @Autowired
  public FeatureSteps(FeatureGateway featureGateway, FeaturesProperties featuresProperties) {
    this.featureGateway = featureGateway;
    this.featuresProperties = featuresProperties;
  }

  @Given("^the feature ([^\"]*) is (ENABLE|DISABLE)$")
  public void theFeatureIs(String name, FeatureStatus status) {
    if (FeatureStatus.ENABLE.equals(status)) {
      featureGateway.enable(name);
    } else {
      featureGateway.disable(name);
    }
  }

  @Given("^the features toggle with status$")
  public void theFeaturesToggleWithStatus(List<Feature> features) {
    features.forEach(
        feature -> {
          if (FeatureStatus.ENABLE.equals(feature.getStatus())) {
            featureGateway.enable(feature.getName());
          } else {
            featureGateway.disable(feature.getName());
          }
        });
  }
}
