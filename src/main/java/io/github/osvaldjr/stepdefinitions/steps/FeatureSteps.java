package io.github.osvaldjr.stepdefinitions.steps;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Given;
import io.github.osvaldjr.domains.Feature;
import io.github.osvaldjr.domains.FeatureStatus;
import io.github.osvaldjr.domains.properties.FeaturesProperties;
import io.github.osvaldjr.gateways.FeatureGateway;

public class FeatureSteps {

  private final FeatureGateway featureGateway;
  private final FeaturesProperties featuresProperties;

  @Autowired
  public FeatureSteps(FeatureGateway featureGateway, FeaturesProperties featuresProperties) {
    this.featureGateway = featureGateway;
    this.featuresProperties = featuresProperties;
  }

  @Given("^the feature ([^\"]*) is (ENABLE|DISABLE)$")
  public void theFeatureIs(String name, FeatureStatus status) {
    String key = featuresProperties.getFeatures().get(name);
    if (FeatureStatus.ENABLE.equals(status)) {
      featureGateway.enable(key);
    } else {
      featureGateway.disable(key);
    }
  }

  @Given("^the features toggle with status$")
  public void theFeaturesToggleWithStatus(List<Feature> features) {
    features.forEach(
        feature -> {
          String key = featuresProperties.getFeatures().get(feature.getName());
          if (FeatureStatus.ENABLE.equals(feature.getStatus())) {
            featureGateway.enable(key);
          } else {
            featureGateway.disable(key);
          }
        });
  }
}
