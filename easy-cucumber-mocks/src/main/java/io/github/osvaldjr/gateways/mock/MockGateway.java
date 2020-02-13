package io.github.osvaldjr.gateways.mock;

import io.github.osvaldjr.objects.StubbyRequest;

public interface MockGateway {
  Object createStubbyRequest(
      StubbyRequest.RequestBody request, StubbyRequest.ResponseBody response);

  void deleteAllServices();

  Integer getMockHits(Object id);
}
