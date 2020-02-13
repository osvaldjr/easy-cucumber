package io.github.osvaldjr.mocks.utils;

import io.github.osvaldjr.mocks.objects.StubbyRequest;

public interface Mock {
  Object createStubbyRequest(
      StubbyRequest.RequestBody request, StubbyRequest.ResponseBody response);

  void deleteAllServices();

  Integer getMockHits(Object id);
}
