package br.community.component.test.unit.gateways;

import br.community.component.test.domains.FeaturesProperties;
import br.community.component.test.gateways.FeatureGateway;
import br.community.component.test.unit.UnitTest;
import io.github.glytching.junit.extension.random.Random;
import org.ff4j.FF4j;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

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
