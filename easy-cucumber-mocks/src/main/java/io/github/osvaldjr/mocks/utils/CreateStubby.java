package io.github.osvaldjr.mocks.utils;

import io.github.osvaldjr.mocks.objects.StubbyRequest;
import io.github.osvaldjr.core.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

@Component
public class CreateStubby {

  private final FileUtils fileUtils;
  private final Mock mock;

  @Autowired
  public CreateStubby(FileUtils fileUtils, Mock mock) {
    this.fileUtils = fileUtils;
    this.mock = mock;
  }

  public Object execute(String scenario, String serviceName, String mockName) throws IOException {
    String mockRequestFile = "mocks/" + mockName + "-request.json";
    String mockResponseFile = "mocks/" + mockName + "-response.json";

    StubbyRequest.RequestBody stubbyRequestBody =
        fileUtils.getObjectFromFile(scenario, mockRequestFile, StubbyRequest.RequestBody.class);
    StubbyRequest.ResponseBody stubbyResponseBody =
        fileUtils.getObjectFromFile(scenario, mockResponseFile, StubbyRequest.ResponseBody.class);

    assertNotNull("url cannot be null in create mock", stubbyRequestBody.getUrl());
    assertNotNull("method cannot be null in create mock", stubbyRequestBody.getMethod());

    stubbyRequestBody.setUrl(getUrl(serviceName, stubbyRequestBody));
    return mock.createStubbyRequest(stubbyRequestBody, stubbyResponseBody);
  }

  private String getUrl(String serviceName, StubbyRequest.RequestBody stubbyRequestBody) {
    return serviceName + stubbyRequestBody.getUrl();
  }
}
