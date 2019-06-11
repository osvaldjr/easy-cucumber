package br.community.component.test.unit.gateways.stubby;

import br.community.component.test.gateways.feign.StubbyClient;
import br.community.component.test.gateways.stubby.StubbyGateway;
import br.community.component.test.gateways.stubby.jsons.StubbyRequest;
import br.community.component.test.gateways.stubby.jsons.StubbyRequestBody;
import br.community.component.test.gateways.stubby.jsons.StubbyResponse;
import br.community.component.test.gateways.stubby.jsons.StubbyResponseBody;
import br.community.component.test.unit.UnitTest;
import io.github.glytching.junit.extension.random.Random;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class StubbyGatewayTest extends UnitTest {

  @Mock StubbyClient stubbyClient;
  @InjectMocks StubbyGateway stubbyGateway;
  @Captor private ArgumentCaptor<Integer> integerArgumentCaptor;
  @Captor private ArgumentCaptor<StubbyRequest> stubbyRequestArgumentCaptor;

  @Test
  void shouldGetStubbyResponse(@Random Integer id, @Random StubbyResponse stubbyResponseMock) {
    when(stubbyClient.getService(id)).thenReturn(stubbyResponseMock);

    StubbyResponse stubbyResponse = stubbyGateway.getStubbyResponse(id);

    assertThat(stubbyResponse, equalTo(stubbyResponseMock));
    verify(stubbyClient, times(1)).getService(id);
  }

  @Test
  void shouldDeleteAllServices(@Random StubbyResponse stubbyResponseMock) {
    List<StubbyResponse> allServices = Arrays.asList(stubbyResponseMock);
    when(stubbyClient.getAllServices()).thenReturn(allServices);

    stubbyGateway.deleteAllServices();

    verify(stubbyClient, times(1)).delete(integerArgumentCaptor.capture());
    verify(stubbyClient, times(1)).getAllServices();
    assertThat(integerArgumentCaptor.getAllValues().get(0), equalTo(stubbyResponseMock.getId()));
  }

  @Test
  void shouldDeleteAllServicesWithEmptyService(@Random StubbyResponse stubbyResponseMock) {
    when(stubbyClient.getAllServices()).thenReturn(null);

    stubbyGateway.deleteAllServices();

    verify(stubbyClient, never()).delete(any());
    verify(stubbyClient, times(1)).getAllServices();
  }

  @Test
  void shouldCreateStubbyRequest(
      @Random StubbyRequestBody request, @Random StubbyResponseBody response) {

    ResponseEntity<StubbyResponse> responseEntity =
        ResponseEntity.ok().header("location", "localhost:8080/1").build();
    when(stubbyClient.create(stubbyRequestArgumentCaptor.capture())).thenReturn(responseEntity);

    Integer stubbyId = stubbyGateway.createStubbyRequest(request, response);

    verify(stubbyClient, times(1)).create(any(StubbyRequest.class));
    assertThat(stubbyRequestArgumentCaptor.getValue().getRequest(), equalTo(request));
    assertThat(stubbyRequestArgumentCaptor.getValue().getResponse(), equalTo(response));
    assertThat(stubbyId, equalTo(1));
  }

  @Test
  void shouldCreateStubbyRequestWithInvalidLocation(
      @Random StubbyRequestBody request, @Random StubbyResponseBody response) {

    ResponseEntity<StubbyResponse> responseEntity =
        ResponseEntity.ok().header("location", "").build();
    when(stubbyClient.create(stubbyRequestArgumentCaptor.capture())).thenReturn(responseEntity);

    Integer stubbyId = stubbyGateway.createStubbyRequest(request, response);

    verify(stubbyClient, times(1)).create(any(StubbyRequest.class));
    assertThat(stubbyRequestArgumentCaptor.getValue().getRequest(), equalTo(request));
    assertThat(stubbyRequestArgumentCaptor.getValue().getResponse(), equalTo(response));
    assertThat(stubbyId, nullValue());
  }
}
