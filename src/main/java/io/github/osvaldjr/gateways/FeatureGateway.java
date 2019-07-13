package io.github.osvaldjr.gateways;

import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeatureGateway {

  private final FF4j ff4j;

  @Autowired
  public FeatureGateway(FF4j ff4j) {
    this.ff4j = ff4j;
  }

  public void enable(String key) {
    ff4j.enable(key);
  }

  public void disable(String key) {
    ff4j.disable(key);
  }
}
