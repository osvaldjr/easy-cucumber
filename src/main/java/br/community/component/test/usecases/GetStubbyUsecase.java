package br.community.component.test.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.community.component.test.gateways.stubby.StubbyGateway;
import br.community.component.test.gateways.stubby.jsons.StubbyResponse;

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
