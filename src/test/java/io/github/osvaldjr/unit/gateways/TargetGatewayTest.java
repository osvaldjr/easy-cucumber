package io.github.osvaldjr.unit.gateways;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.gateways.TargetGateway;
import io.github.osvaldjr.gateways.feign.TargetClient;
import io.github.osvaldjr.unit.UnitTest;

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

    ResponseEntity response = targetGateway.post(uri, body, headers);

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

    ResponseEntity response = targetGateway.delete(uri, body, headers);

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

    ResponseEntity response = targetGateway.put(uri, body, headers);

    verify(feignClient, times(1)).put(uri, body, headers);
    assertThat(response, equalTo(responseEntity));
  }
}
