package io.github.osvaldjr.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.gateways.mock.MockGateway;

@Component
public class GetMockHitsUseCase {

  private MockGateway mockGateway;

  @Autowired
  public GetMockHitsUseCase(MockGateway mockGateway) {
    this.mockGateway = mockGateway;
  }

  public <T> Integer execute(T id) {
    return mockGateway.getMockHits(id);
  }
}
