package br.community.component.test.gateways;

import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.community.component.test.domains.FeaturesProperties;

@Component
public class FeatureGateway {

  private final FF4j ff4j;
  private final FeaturesProperties featuresProperties;

  @Autowired
  public FeatureGateway(FF4j ff4j, FeaturesProperties featuresProperties) {
    this.ff4j = ff4j;
    this.featuresProperties = featuresProperties;
  }

  public void enable(String key) {
    ff4j.enable(key);
  }

  public void disable(String key) {
    ff4j.disable(key);
  }

  public void enableAllFeatures() {
    featuresProperties.getFeatures().values().forEach(this::enable);
  }

  public void disableAllFeatures() {
    featuresProperties.getFeatures().values().forEach(this::disable);
  }
}
