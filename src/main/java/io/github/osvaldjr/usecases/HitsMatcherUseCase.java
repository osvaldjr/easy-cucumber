package io.github.osvaldjr.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.gateways.stubby.StubbyGateway;

@Component
public class HitsMatcherUseCase {

  private StubbyGateway stubbyGateway;

  @Autowired
  public HitsMatcherUseCase(StubbyGateway stubbyGateway) {
    this.stubbyGateway = stubbyGateway;
  }

  public boolean execute(String id, Integer hits) {
    return stubbyGateway.getStubbyResponse(id).getHits().equals(hits);
  }
}
