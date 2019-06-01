package br.community.component.test.stepdefinitions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.community.component.test.domains.Feature;
import br.community.component.test.domains.FeatureStatus;
import br.community.component.test.domains.FeaturesProperties;
import br.community.component.test.gateways.FeatureGateway;
import cucumber.api.java.en.Given;

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
          String key = featuresProperties.getFeatures().get(feature);
          if (FeatureStatus.ENABLE.equals(feature.getStatus())) {
            featureGateway.enable(key);
          } else {
            featureGateway.disable(key);
          }
        });
  }
}
