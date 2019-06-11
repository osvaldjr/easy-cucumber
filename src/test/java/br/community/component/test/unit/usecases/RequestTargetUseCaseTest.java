package br.community.component.test.unit.usecases;

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
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.MethodNotAllowedException;

import br.community.component.test.domains.TargetRequest;
import br.community.component.test.gateways.TargetGateway;
import br.community.component.test.unit.UnitTest;
import br.community.component.test.usecases.RequestTargetUseCase;
import io.github.glytching.junit.extension.random.Random;

class RequestTargetUseCaseTest extends UnitTest {

  @Mock private TargetGateway targetGateway;
  @InjectMocks private RequestTargetUseCase requestTargetUseCase;

  @Test
  void shouldExecuteGet(
      @Random TargetRequest targetRequest, @Random ResponseEntity responseEntity) {
    targetRequest.setMethod(GET.name());
    when(targetGateway.get(targetRequest.getUrl(), targetRequest.getHeaders()))
        .thenReturn(responseEntity);

    ResponseEntity response = requestTargetUseCase.execute(targetRequest);

    assertThat(response, equalTo(responseEntity));
    verify(targetGateway, times(1)).get(anyString(), anyMap());
  }

  @Test
  void shouldExecutePost(
      @Random TargetRequest targetRequest,
      @Random ResponseEntity responseEntity,
      @Random Object body) {
    targetRequest.setMethod(POST.name());
    targetRequest.setBody(body);
    when(targetGateway.post(targetRequest.getUrl(), body, targetRequest.getHeaders()))
        .thenReturn(responseEntity);

    ResponseEntity response = requestTargetUseCase.execute(targetRequest);

    assertThat(response, equalTo(responseEntity));
    verify(targetGateway, times(1)).post(any(), any(), any());
  }

  @Test
  void shouldExecutePut(
      @Random TargetRequest targetRequest,
      @Random ResponseEntity responseEntity,
      @Random Object body) {
    targetRequest.setMethod(PUT.name());
    targetRequest.setBody(body);
    when(targetGateway.put(targetRequest.getUrl(), body, targetRequest.getHeaders()))
        .thenReturn(responseEntity);

    ResponseEntity response = requestTargetUseCase.execute(targetRequest);

    assertThat(response, equalTo(responseEntity));
    verify(targetGateway, times(1)).put(any(), any(), any());
  }

  @Test
  void shouldExecuteDelete(
      @Random TargetRequest targetRequest,
      @Random ResponseEntity responseEntity,
      @Random Object body) {
    targetRequest.setMethod(DELETE.name());
    targetRequest.setBody(body);
    when(targetGateway.delete(targetRequest.getUrl(), body, targetRequest.getHeaders()))
        .thenReturn(responseEntity);

    ResponseEntity response = requestTargetUseCase.execute(targetRequest);

    assertThat(response, equalTo(responseEntity));
    verify(targetGateway, times(1)).delete(any(), any(), any());
  }

  @Test
  void shouldExecuteWithInvalidMethod(@Random TargetRequest targetRequest) {
    targetRequest.setMethod(HEAD.name());

    MethodNotAllowedException throwable =
        assertThrows(
            MethodNotAllowedException.class, () -> requestTargetUseCase.execute(targetRequest));

    assertThat(throwable.getHttpMethod(), equalTo(HEAD.name()));
    assertThat(throwable.getSupportedMethods(), containsInAnyOrder(GET, POST, PUT, DELETE));
    verify(targetGateway, never()).delete(any(), any(), any());
  }
}
