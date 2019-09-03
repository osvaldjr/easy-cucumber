package io.github.osvaldjr.unit.usecases;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.HEAD;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.MethodNotAllowedException;

import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.domains.TargetRequest;
import io.github.osvaldjr.gateways.TargetGateway;
import io.github.osvaldjr.unit.UnitTest;
import io.github.osvaldjr.usecases.RequestTargetUseCase;

class RequestTargetUseCaseTest extends UnitTest {

  @Mock private TargetGateway targetGateway;
  @InjectMocks private RequestTargetUseCase requestTargetUseCase;

  @Test
  void shouldExecuteGet(
      @Random TargetRequest targetRequest, @Random ResponseEntity responseEntity) {
    targetRequest.setMethod(GET.name());
    when(targetGateway.get(
            targetRequest.getHost(),
            targetRequest.getUrl(),
            targetRequest.getHeaders(),
            targetRequest.getQueryParameters()))
        .thenReturn(responseEntity);

    ResponseEntity response = requestTargetUseCase.execute(targetRequest);

    assertThat(response, equalTo(responseEntity));
    verify(targetGateway, times(1)).get(anyString(), anyString(), anyMap(), anyMap());
  }

  @Test
  void shouldExecutePost(
      @Random TargetRequest targetRequest,
      @Random ResponseEntity responseEntity,
      @Random Object body) {
    targetRequest.setMethod(POST.name());
    targetRequest.setBody(body);
    when(targetGateway.post(
            targetRequest.getHost(),
            targetRequest.getUrl(),
            body,
            targetRequest.getHeaders(),
            targetRequest.getQueryParameters()))
        .thenReturn(responseEntity);

    ResponseEntity response = requestTargetUseCase.execute(targetRequest);

    assertThat(response, equalTo(responseEntity));
    verify(targetGateway, times(1)).post(any(), any(), any(), any(), anyMap());
  }

  @Test
  void shouldExecutePut(
      @Random TargetRequest targetRequest,
      @Random ResponseEntity responseEntity,
      @Random Object body) {
    targetRequest.setMethod(PUT.name());
    targetRequest.setBody(body);
    when(targetGateway.put(
            targetRequest.getHost(),
            targetRequest.getUrl(),
            body,
            targetRequest.getHeaders(),
            targetRequest.getQueryParameters()))
        .thenReturn(responseEntity);

    ResponseEntity response = requestTargetUseCase.execute(targetRequest);

    assertThat(response, equalTo(responseEntity));
    verify(targetGateway, times(1)).put(any(), any(), any(), any(), any());
  }

  @Test
  void shouldExecuteDelete(
      @Random TargetRequest targetRequest,
      @Random ResponseEntity responseEntity,
      @Random Object body) {
    targetRequest.setMethod(DELETE.name());
    targetRequest.setBody(body);
    targetRequest.setHost(null);
    ReflectionTestUtils.setField(requestTargetUseCase, "targetHost", "http://localhost:9001");
    when(targetGateway.delete(
            "http://localhost:9001",
            targetRequest.getUrl(),
            body,
            targetRequest.getHeaders(),
            targetRequest.getQueryParameters()))
        .thenReturn(responseEntity);

    ResponseEntity response = requestTargetUseCase.execute(targetRequest);

    assertThat(response, equalTo(responseEntity));
    verify(targetGateway, times(1)).delete(any(), any(), any(), any(), anyMap());
  }

  @Test
  void shouldExecuteWithInvalidMethod(@Random TargetRequest targetRequest) {
    targetRequest.setMethod(HEAD.name());

    MethodNotAllowedException throwable =
        assertThrows(
            MethodNotAllowedException.class, () -> requestTargetUseCase.execute(targetRequest));

    assertThat(throwable.getHttpMethod(), equalTo(HEAD.name()));
    assertThat(throwable.getSupportedMethods(), containsInAnyOrder(GET, POST, PUT, DELETE, PATCH));
    verify(targetGateway, never()).delete(any(), any(), any(), any(), anyMap());
  }

  @Test
  void shouldInvalidUrlRequest(@Random TargetRequest targetRequest) {
    targetRequest.setUrl(null);

    AssertionError throwable =
        assertThrows(AssertionError.class, () -> requestTargetUseCase.execute(targetRequest));

    assertThat(throwable.getMessage(), equalTo("url cannot be null in make request"));
  }

  @Test
  void shouldInvalidMethodRequest(@Random TargetRequest targetRequest) {
    targetRequest.setMethod(null);

    AssertionError throwable =
        assertThrows(AssertionError.class, () -> requestTargetUseCase.execute(targetRequest));

    assertThat(throwable.getMessage(), equalTo("method cannot be null in make request"));
  }

  @Test
  void shouldInvalidHostRequest(@Random TargetRequest targetRequest) {
    targetRequest.setHost(null);

    AssertionError throwable =
        assertThrows(AssertionError.class, () -> requestTargetUseCase.execute(targetRequest));

    assertThat(throwable.getMessage(), equalTo("host cannot be null in make request"));
  }

  @Test
  void shouldExecutePatch(
      @Random TargetRequest targetRequest,
      @Random ResponseEntity responseEntity,
      @Random Object body) {
    targetRequest.setMethod(PATCH.name());
    targetRequest.setBody(body);
    targetRequest.setHost(null);
    ReflectionTestUtils.setField(requestTargetUseCase, "targetHost", "http://localhost:9001");
    when(targetGateway.patch(
            "http://localhost:9001",
            targetRequest.getUrl(),
            body,
            targetRequest.getHeaders(),
            targetRequest.getQueryParameters()))
        .thenReturn(responseEntity);

    ResponseEntity response = requestTargetUseCase.execute(targetRequest);

    assertThat(response, equalTo(responseEntity));
    verify(targetGateway, times(1)).patch(any(), any(), any(), any(), any());
  }
}
