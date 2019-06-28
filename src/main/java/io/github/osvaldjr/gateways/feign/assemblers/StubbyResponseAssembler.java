package io.github.osvaldjr.gateways.feign.assemblers;

import org.springframework.stereotype.Component;

import io.github.osvaldjr.domains.StubbyResponse;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyJsonResponse;

@Component
public class StubbyResponseAssembler {
  public StubbyResponse assemble(StubbyJsonResponse stubbyJsonResponse) {
    return StubbyResponse.builder()
        .hits(stubbyJsonResponse.getHits())
        .id(stubbyJsonResponse.getId())
        .build();
  }
}
