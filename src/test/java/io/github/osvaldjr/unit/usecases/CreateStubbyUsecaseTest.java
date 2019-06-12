package io.github.osvaldjr.unit.usecases;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.gateways.FileGateway;
import io.github.osvaldjr.gateways.stubby.StubbyGateway;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyRequestBody;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyResponseBody;
import io.github.osvaldjr.unit.UnitTest;
import io.github.osvaldjr.usecases.CreateStubbyUsecase;

class CreateStubbyUsecaseTest extends UnitTest {

  @Mock private FileGateway fileGateway;
  @Mock private StubbyGateway stubbyGateway;
  @InjectMocks private CreateStubbyUsecase createStubbyUsecase;
  @Captor private ArgumentCaptor<StubbyRequestBody> stubbyRequestBodyArgumentCaptor;
  @Captor private ArgumentCaptor<StubbyResponseBody> stubbyResponseBodyArgumentCaptor;

  @Test
  void shouldExecute(
      @Random String scenario,
      @Random String serviceName,
      @Random String mockName,
      @Random StubbyRequestBody stubbyRequestBody,
      @Random StubbyResponseBody stubbyResponseBody,
      @Random Integer id)
      throws IOException {
    String mockRequestFile = "mocks/" + mockName + "-request";
    String mockResponseFile = "mocks/" + mockName + "-response";
    String url = stubbyRequestBody.getUrl();
    when(fileGateway.getObjectFromFile(scenario, mockRequestFile, StubbyRequestBody.class))
        .thenReturn(stubbyRequestBody);
    when(fileGateway.getObjectFromFile(scenario, mockResponseFile, StubbyResponseBody.class))
        .thenReturn(stubbyResponseBody);
    when(stubbyGateway.createStubbyRequest(
            stubbyRequestBodyArgumentCaptor.capture(), stubbyResponseBodyArgumentCaptor.capture()))
        .thenReturn(id);

    Integer stubbyId = createStubbyUsecase.execute(scenario, serviceName, mockName);

    assertThat(stubbyId, equalTo(id));
    StubbyRequestBody requestBody = stubbyRequestBodyArgumentCaptor.getValue();
    assertThat(requestBody.getUrl(), equalTo(serviceName + url));
    assertThat(requestBody.getHeaders(), equalTo(stubbyRequestBody.getHeaders()));
    assertThat(requestBody.getMethod(), equalTo(stubbyRequestBody.getMethod()));
    assertThat(requestBody.getQueryParams(), equalTo(stubbyRequestBody.getQueryParams()));
    assertThat(requestBody.getBody(), equalTo(stubbyRequestBody.getBody()));
    StubbyResponseBody responseBody = stubbyResponseBodyArgumentCaptor.getValue();
    assertThat(responseBody, equalTo(stubbyResponseBody));
    verify(fileGateway, times(2)).getObjectFromFile(anyString(), anyString(), any());
    verify(stubbyGateway, times(1))
        .createStubbyRequest(any(StubbyRequestBody.class), any(StubbyResponseBody.class));
  }
}
