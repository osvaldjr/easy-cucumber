package io.github.osvaldjr.utils;

import io.github.osvaldjr.objects.StubbyRequest;

public interface Mock {
  Object createStubbyRequest(
      StubbyRequest.RequestBody request, StubbyRequest.ResponseBody response);

  void deleteAllServices();

  Integer getMockHits(Object id);
}
