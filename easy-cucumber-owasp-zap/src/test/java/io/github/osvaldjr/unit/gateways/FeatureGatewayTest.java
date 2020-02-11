package io.github.osvaldjr.unit.gateways;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.ff4j.FF4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.gateways.FeatureGateway;
import io.github.osvaldjr.unit.UnitTest;

class FeatureGatewayTest extends UnitTest {

  @Mock private FF4j ff4j;
  @InjectMocks private FeatureGateway featureGateway;

  @Test
  void shouldEnableFeature(@Random String key) {
    featureGateway.enable(key);

    verify(ff4j, times(1)).enable(key);
  }

  @Test
  void shouldDisableFeature(@Random String key) {
    featureGateway.disable(key);

    verify(ff4j, times(1)).disable(key);
  }
}
