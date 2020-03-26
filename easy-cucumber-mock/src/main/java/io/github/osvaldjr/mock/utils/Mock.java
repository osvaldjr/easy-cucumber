package io.github.osvaldjr.mock.utils;

import io.github.osvaldjr.mock.objects.StubbyRequest;

public interface Mock {
  Object createStubbyRequest(
      StubbyRequest.RequestBody request, StubbyRequest.ResponseBody response);

  void deleteAllServices();

  Integer getMockHits(Object id);
}
