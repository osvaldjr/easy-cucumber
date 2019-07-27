package io.github.osvaldjr.unit.gateways;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.gateways.TargetGateway;
import io.github.osvaldjr.gateways.feign.TargetClient;
import io.github.osvaldjr.unit.UnitTest;

public class TargetGatewayTest extends UnitTest {

  @Mock private TargetClient feignClient;
  @Mock private Encoder encoder;
  @Mock private Decoder decoder;
  @Mock private Contract contract;
  @Mock private ErrorDecoder errorDecoder;
  @InjectMocks private TargetGateway targetGateway;
  @Mock private Feign.Builder feignBuilder;

  @BeforeEach
  void before() {
    ReflectionTestUtils.setField(targetGateway, "feignBuilder", feignBuilder);
    when(feignBuilder.target(eq(TargetClient.class), anyString())).thenReturn(feignClient);
  }

  @Test
  void shouldGet(
      @Random String host,
      @Random String uri,
      @Random Map<String, String> headers,
      @Random ResponseEntity responseEntity) {

    when(feignClient.get(uri, headers)).thenReturn(responseEntity);
    ResponseEntity response = targetGateway.get(host, uri, headers);

    verify(feignClient, times(1)).get(uri, headers);
    verify(feignBuilder, times(1)).target(TargetClient.class, host);
    assertThat(response, equalTo(responseEntity));
  }

  @Test
  void shouldPost(
      @Random String host,
      @Random String uri,
      @Random Object body,
      @Random Map<String, String> headers,
      @Random ResponseEntity responseEntity) {
    when(feignClient.post(uri, body, headers)).thenReturn(responseEntity);

    ResponseEntity response = targetGateway.post(host, uri, body, headers);

    verify(feignClient, times(1)).post(uri, body, headers);
    verify(feignBuilder, times(1)).target(TargetClient.class, host);
    assertThat(response, equalTo(responseEntity));
  }

  @Test
  void shouldDelete(
      @Random String host,
      @Random String uri,
      @Random Object body,
      @Random Map<String, String> headers,
      @Random ResponseEntity responseEntity) {
    when(feignClient.delete(uri, body, headers)).thenReturn(responseEntity);

    ResponseEntity response = targetGateway.delete(host, uri, body, headers);

    verify(feignClient, times(1)).delete(uri, body, headers);
    verify(feignClient, never()).delete(any(), any());
    verify(feignBuilder, times(1)).target(TargetClient.class, host);
    assertThat(response, equalTo(responseEntity));
  }

  @Test
  void shouldDeleteWithNullBody(
      @Random String host,
      @Random String uri,
      @Random Map<String, String> headers,
      @Random ResponseEntity responseEntity) {
    when(feignClient.delete(uri, headers)).thenReturn(responseEntity);

    ResponseEntity response = targetGateway.delete(host, uri, null, headers);

    verify(feignClient, times(1)).delete(uri, headers);
    verify(feignClient, never()).delete(any(), any(), any());
    verify(feignBuilder, times(1)).target(TargetClient.class, host);
    assertThat(response, equalTo(responseEntity));
  }

  @Test
  void shouldPut(
      @Random String host,
      @Random String uri,
      @Random Object body,
      @Random Map<String, String> headers,
      @Random ResponseEntity responseEntity) {
    when(feignClient.put(uri, body, headers)).thenReturn(responseEntity);

    ResponseEntity response = targetGateway.put(host, uri, body, headers);

    verify(feignClient, times(1)).put(uri, body, headers);
    verify(feignBuilder, times(1)).target(TargetClient.class, host);
    assertThat(response, equalTo(responseEntity));
  }
}
