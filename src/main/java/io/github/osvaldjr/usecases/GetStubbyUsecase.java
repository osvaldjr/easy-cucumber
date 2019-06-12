package io.github.osvaldjr.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.gateways.stubby.StubbyGateway;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyResponse;

@Component
public class GetStubbyUsecase {

  private StubbyGateway stubbyGateway;

  @Autowired
  public GetStubbyUsecase(StubbyGateway stubbyGateway) {
    this.stubbyGateway = stubbyGateway;
  }

  public StubbyResponse execute(Integer id) {
    return stubbyGateway.getStubbyResponse(id);
  }
}
