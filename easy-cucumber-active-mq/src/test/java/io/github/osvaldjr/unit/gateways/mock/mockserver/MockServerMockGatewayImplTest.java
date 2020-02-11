package io.github.osvaldjr.unit.gateways.mock.mockserver;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockserver.client.MockServerClient;
import org.mockserver.matchers.TimeToLive;
import org.mockserver.matchers.Times;
import org.mockserver.mock.Expectation;
import org.mockserver.model.HttpRequest;

import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.domains.StubbyRequest;
import io.github.osvaldjr.gateways.mock.mockserver.MockServerMockGatewayImpl;
import io.github.osvaldjr.gateways.mock.mockserver.assemblers.ExpectationRequestAssembler;
import io.github.osvaldjr.unit.UnitTest;

class MockServerMockGatewayImplTest extends UnitTest {

  @Mock MockServerClient mockServerClient;
  @Mock ExpectationRequestAssembler expectationRequestAssembler;
  @InjectMocks MockServerMockGatewayImpl mockServerMockGateway;

  @Test
  void shouldCreateStubbyRequest(
      @Random StubbyRequest.RequestBody request, @Random StubbyRequest.ResponseBody response) {

    HttpRequest httpRequest = new HttpRequest();
    Expectation expectation =
        new Expectation(httpRequest, Times.exactly(10), TimeToLive.unlimited());

    when(expectationRequestAssembler.assemble(any(), any(), anyInt())).thenReturn(expectation);

    HttpRequest stubbyRequest = mockServerMockGateway.createStubbyRequest(request, response);

    assertThat(stubbyRequest, notNullValue());
    assertThat(stubbyRequest, equalTo(expectation.getHttpRequest()));
    verify(expectationRequestAssembler, times(1)).assemble(any(), any(), anyInt());
    verify(mockServerClient, times(1)).sendExpectation(expectation);
  }

  @Test
  void shouldDeleteAllServices() {
    mockServerMockGateway.deleteAllServices();
    verify(mockServerClient, times(1)).reset();
  }

  @Test
  void shouldReturnMockHitsCorrectly() {
    HttpRequest httpRequest = new HttpRequest();
    Expectation[] expectations = {
      new Expectation(httpRequest, Times.exactly(10), TimeToLive.unlimited())
    };
    when(mockServerClient.retrieveActiveExpectations(any(HttpRequest.class)))
        .thenReturn(expectations);

    Integer mockHits = mockServerMockGateway.getMockHits(httpRequest);
    assertThat(mockHits, notNullValue());
    assertThat(mockHits, equalTo(Integer.MAX_VALUE - 10));
  }

  @Test
  void shouldReturnMockHitsCorrectlyWhenMockServerDoesNotReturnExpectation() {
    HttpRequest httpRequest = new HttpRequest();
    Expectation[] expectations = {};
    when(mockServerClient.retrieveActiveExpectations(any(HttpRequest.class)))
        .thenReturn(expectations);

    Integer mockHits = mockServerMockGateway.getMockHits(httpRequest);
    assertThat(mockHits, notNullValue());
    assertThat(mockHits, equalTo(Integer.MAX_VALUE));
  }
}
