package io.github.osvaldjr.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.gateways.mock.MockGateway;

@Component
public class HitsMatcherUseCase {

  private MockGateway mockGateway;

  @Autowired
  public HitsMatcherUseCase(MockGateway mockGateway) {
    this.mockGateway = mockGateway;
  }

  public <T> boolean execute(T id, Integer hits) {
    return mockGateway.getMockHits(id).equals(hits);
  }
}
