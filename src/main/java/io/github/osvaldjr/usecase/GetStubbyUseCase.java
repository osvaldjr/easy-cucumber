package io.github.osvaldjr.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.domains.StubbyResponse;
import io.github.osvaldjr.gateways.stubby.StubbyGateway;

@Component
public class GetStubbyUseCase {

  private StubbyGateway stubbyGateway;

  @Autowired
  public GetStubbyUseCase(StubbyGateway stubbyGateway) {
    this.stubbyGateway = stubbyGateway;
  }

  public StubbyResponse execute(Integer id) {
    return stubbyGateway.getStubbyResponse(id);
  }
}
