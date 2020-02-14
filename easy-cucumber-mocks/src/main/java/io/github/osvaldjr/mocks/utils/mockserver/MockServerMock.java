package io.github.osvaldjr.mocks.utils.mockserver;

import org.mockserver.client.MockServerClient;
import org.mockserver.mock.Expectation;
import org.mockserver.model.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.mocks.objects.StubbyRequest;
import io.github.osvaldjr.mocks.utils.Mock;
import io.github.osvaldjr.mocks.utils.mockserver.assemblers.ExpectationRequestAssembler;

@Component
@ConditionalOnProperty("dependencies.mockserver.port")
public class MockServerMock implements Mock {
  private static final Integer MAX_HITS = Integer.MAX_VALUE;

  private final MockServerClient mockServerClient;
  private final ExpectationRequestAssembler expectationRequestAssembler;

  @Autowired
  public MockServerMock(
      MockServerClient mockServerClient, ExpectationRequestAssembler expectationRequestAssembler) {
    this.mockServerClient = mockServerClient;
    this.expectationRequestAssembler = expectationRequestAssembler;
  }

  @Override
  public HttpRequest createStubbyRequest(
      StubbyRequest.RequestBody request, StubbyRequest.ResponseBody response) {
    Expectation expectation = expectationRequestAssembler.assemble(request, response, MAX_HITS);
    mockServerClient.sendExpectation(expectation);
    return expectation.getHttpRequest();
  }

  @Override
  public void deleteAllServices() {
    mockServerClient.reset();
  }

  @Override
  public Integer getMockHits(Object requestIdentifier) {
    Expectation[] expectations =
        mockServerClient.retrieveActiveExpectations((HttpRequest) requestIdentifier);

    int remainingTimes = 0;
    if (expectations.length > 0) {
      remainingTimes = expectations[0].getTimes().getRemainingTimes();
    }
    return MAX_HITS - remainingTimes;
  }
}
