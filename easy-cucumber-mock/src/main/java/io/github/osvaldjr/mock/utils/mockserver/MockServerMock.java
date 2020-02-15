package io.github.osvaldjr.mock.utils.mockserver;

import org.mockserver.client.MockServerClient;
import org.mockserver.mock.Expectation;
import org.mockserver.model.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.mock.objects.StubbyRequest;
import io.github.osvaldjr.mock.utils.Mock;
import io.github.osvaldjr.mock.utils.mockserver.assemblers.ExpectationRequestAssembler;

@Component
@ConditionalOnProperty("dependencies.mockserver.port")
public class MockServerMock implements Mock {

  private static final Integer MAX_HITS = Integer.MAX_VALUE;

  private final MockServerClient mockServerClient;
  private final ExpectationRequestAssembler expectationRequestAssembler;

  @Autowired
  public MockServerMock(
      ExpectationRequestAssembler expectationRequestAssembler,
      @Value("${dependencies.mockserver.host:localhost}") String mockServerHost,
      @Value("${dependencies.mockserver.port:}") Integer mockServerPort) {
    this.mockServerClient = new MockServerClient(mockServerHost, mockServerPort);
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
