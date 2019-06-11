package br.community.component.test.unit.gateways;

import br.community.component.test.gateways.TargetGateway;
import br.community.component.test.gateways.feign.TargetClient;
import br.community.component.test.unit.UnitTest;
import io.github.glytching.junit.extension.random.Random;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class TargetGatewayTest extends UnitTest {

  @Mock private TargetClient feignClient;
  @InjectMocks private TargetGateway targetGateway;

  @Test
  void shouldGet(
      @Random String uri,
      @Random Map<String, String> headers,
      @Random ResponseEntity responseEntity) {
    when(feignClient.get(uri, headers)).thenReturn(responseEntity);

    ResponseEntity response = targetGateway.get(uri, headers);

    verify(feignClient, times(1)).get(uri, headers);
    assertThat(response, equalTo(responseEntity));
  }

  @Test
  void shouldPost(
      @Random String uri,
      @Random Object body,
      @Random Map<String, String> headers,
      @Random ResponseEntity responseEntity) {
    when(feignClient.post(uri, body, headers)).thenReturn(responseEntity);

    ResponseEntity response = feignClient.post(uri, body, headers);

    verify(feignClient, times(1)).post(uri, body, headers);
    assertThat(response, equalTo(responseEntity));
  }

  @Test
  void shouldDelete(
      @Random String uri,
      @Random Object body,
      @Random Map<String, String> headers,
      @Random ResponseEntity responseEntity) {
    when(feignClient.delete(uri, body, headers)).thenReturn(responseEntity);

    ResponseEntity response = feignClient.delete(uri, body, headers);

    verify(feignClient, times(1)).delete(uri, body, headers);
    assertThat(response, equalTo(responseEntity));
  }

  @Test
  void shouldPut(
      @Random String uri,
      @Random Object body,
      @Random Map<String, String> headers,
      @Random ResponseEntity responseEntity) {
    when(feignClient.put(uri, body, headers)).thenReturn(responseEntity);

    ResponseEntity response = feignClient.put(uri, body, headers);

    verify(feignClient, times(1)).put(uri, body, headers);
    assertThat(response, equalTo(responseEntity));
  }
}
