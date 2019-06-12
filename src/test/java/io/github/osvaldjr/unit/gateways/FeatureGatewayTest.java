package io.github.osvaldjr.unit.gateways;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.ff4j.FF4j;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.domains.FeaturesProperties;
import io.github.osvaldjr.gateways.FeatureGateway;
import io.github.osvaldjr.unit.UnitTest;

class FeatureGatewayTest extends UnitTest {

  @Mock private FF4j ff4j;
  @Mock private FeaturesProperties featuresProperties;
  @InjectMocks private FeatureGateway featureGateway;
  @Captor private ArgumentCaptor<String> keyArgumentCaptor;

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

  @Test
  void shouldEnableAllFeatures() {
    Map<String, String> features = new HashMap<>();
    features.put("DETAIL_POKEMON", "detail-pokemon");
    features.put("SHOW_POKEMON", "show-pokemon");
    when(featuresProperties.getFeatures()).thenReturn(features);

    featureGateway.enableAllFeatures();

    verify(ff4j, times(features.size())).enable(keyArgumentCaptor.capture());
    assertThat(keyArgumentCaptor.getAllValues(), equalTo(asList("show-pokemon", "detail-pokemon")));
  }

  @Test
  void shouldDisableAllFeatures() {
    Map<String, String> features = new HashMap<>();
    features.put("SHOW_POKEMON", "show-pokemon");
    features.put("SHOW_DETAIL", "detail_pokemon");
    when(featuresProperties.getFeatures()).thenReturn(features);

    featureGateway.disableAllFeatures();

    verify(ff4j, times(features.size())).disable(keyArgumentCaptor.capture());
    assertThat(keyArgumentCaptor.getAllValues(), equalTo(asList("show-pokemon", "detail_pokemon")));
  }
}
