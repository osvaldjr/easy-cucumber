package io.github.osvaldjr.unit.gateways.stubby;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.domains.StubbyRequest;
import io.github.osvaldjr.domains.StubbyResponse;
import io.github.osvaldjr.gateways.feign.StubbyClient;
import io.github.osvaldjr.gateways.feign.assemblers.StubbyRequestAssembler;
import io.github.osvaldjr.gateways.feign.assemblers.StubbyResponseAssembler;
import io.github.osvaldjr.gateways.stubby.StubbyGateway;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyJsonRequest;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyJsonResponse;
import io.github.osvaldjr.unit.UnitTest;

public class StubbyGatewayTest extends UnitTest {

  @Mock StubbyClient stubbyClient;
  @Mock StubbyResponseAssembler stubbyResponseAssembler;
  @Mock StubbyRequestAssembler stubbyRequestAssembler;
  @InjectMocks StubbyGateway stubbyGateway;
  @Captor private ArgumentCaptor<Integer> integerArgumentCaptor;
  @Captor private ArgumentCaptor<StubbyJsonRequest> stubbyRequestArgumentCaptor;

  @Test
  void shouldGetStubbyResponse(
      @Random Integer id,
      @Random StubbyJsonResponse stubbyJsonResponseMock,
      @Random StubbyResponse stubbyResponseMock) {
    when(stubbyClient.getService(id)).thenReturn(stubbyJsonResponseMock);
    when(stubbyResponseAssembler.assemble(stubbyJsonResponseMock)).thenReturn(stubbyResponseMock);

    StubbyResponse stubbyResponseReturned = stubbyGateway.getStubbyResponse(id);

    assertThat(stubbyResponseReturned, equalTo(stubbyResponseMock));
    verify(stubbyClient, times(1)).getService(id);
    verify(stubbyResponseAssembler, times(1)).assemble(stubbyJsonResponseMock);
  }

  @Test
  void shouldDeleteAllServices(@Random StubbyJsonResponse stubbyResponseMock) {
    List<StubbyJsonResponse> allServices = Arrays.asList(stubbyResponseMock);
    when(stubbyClient.getAllServices()).thenReturn(allServices);

    stubbyGateway.deleteAllServices();

    verify(stubbyClient, times(1)).delete(integerArgumentCaptor.capture());
    verify(stubbyClient, times(1)).getAllServices();
    assertThat(integerArgumentCaptor.getAllValues().get(0), equalTo(stubbyResponseMock.getId()));
  }

  @Test
  void shouldDeleteAllServicesWithEmptyService(@Random StubbyJsonResponse stubbyResponseMock) {
    when(stubbyClient.getAllServices()).thenReturn(null);

    stubbyGateway.deleteAllServices();

    verify(stubbyClient, never()).delete(any());
    verify(stubbyClient, times(1)).getAllServices();
  }

  @Test
  void shouldCreateStubbyRequest(
      @Random StubbyRequest.RequestBody request,
      @Random StubbyRequest.ResponseBody response,
      @Random StubbyJsonRequest stubbyJsonRequestMock) {

    ResponseEntity<StubbyJsonResponse> responseEntity =
        ResponseEntity.ok().header("location", "localhost:8080/1").build();
    when(stubbyClient.create(stubbyRequestArgumentCaptor.capture())).thenReturn(responseEntity);
    when(stubbyRequestAssembler.assemble(request, response)).thenReturn(stubbyJsonRequestMock);

    Integer stubbyId = stubbyGateway.createStubbyRequest(request, response);

    verify(stubbyClient, times(1)).create(any(StubbyJsonRequest.class));
    verify(stubbyRequestAssembler, times(1))
        .assemble(any(StubbyRequest.RequestBody.class), any(StubbyRequest.ResponseBody.class));

    assertThat(
        stubbyRequestArgumentCaptor.getValue().getRequest(),
        equalTo(stubbyJsonRequestMock.getRequest()));
    assertThat(
        stubbyRequestArgumentCaptor.getValue().getResponse(),
        equalTo(stubbyJsonRequestMock.getResponse()));
    assertThat(stubbyId, equalTo(1));
  }

  @Test
  void shouldCreateStubbyRequestWithInvalidLocation(
      @Random StubbyRequest.RequestBody request,
      @Random StubbyRequest.ResponseBody response,
      @Random StubbyJsonRequest stubbyJsonRequestMock) {

    ResponseEntity<StubbyJsonResponse> responseEntity =
        ResponseEntity.ok().header("location", "").build();
    when(stubbyClient.create(stubbyRequestArgumentCaptor.capture())).thenReturn(responseEntity);
    when(stubbyRequestAssembler.assemble(request, response)).thenReturn(stubbyJsonRequestMock);

    Integer stubbyId = stubbyGateway.createStubbyRequest(request, response);

    verify(stubbyClient, times(1)).create(any(StubbyJsonRequest.class));
    assertThat(
        stubbyRequestArgumentCaptor.getValue().getRequest(),
        equalTo(stubbyJsonRequestMock.getRequest()));
    assertThat(
        stubbyRequestArgumentCaptor.getValue().getResponse(),
        equalTo(stubbyJsonRequestMock.getResponse()));
    assertThat(stubbyId, nullValue());
  }
}
