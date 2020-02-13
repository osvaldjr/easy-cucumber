package io.github.osvaldjr.utils;

import io.github.osvaldjr.gateways.FileGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static io.github.osvaldjr.objects.StubbyRequest.RequestBody;
import static io.github.osvaldjr.objects.StubbyRequest.ResponseBody;
import static org.junit.Assert.assertNotNull;

@Component
public class CreateStubby {

  private final FileGateway fileGateway;
  private final Mock mock;

  @Autowired
  public CreateStubby(FileGateway fileGateway, Mock mock) {
    this.fileGateway = fileGateway;
    this.mock = mock;
  }

  public Object execute(String scenario, String serviceName, String mockName) throws IOException {
    String mockRequestFile = "mocks/" + mockName + "-request.json";
    String mockResponseFile = "mocks/" + mockName + "-response.json";

    RequestBody stubbyRequestBody =
        fileGateway.getObjectFromFile(scenario, mockRequestFile, RequestBody.class);
    ResponseBody stubbyResponseBody =
        fileGateway.getObjectFromFile(scenario, mockResponseFile, ResponseBody.class);

    assertNotNull("url cannot be null in create mock", stubbyRequestBody.getUrl());
    assertNotNull("method cannot be null in create mock", stubbyRequestBody.getMethod());

    stubbyRequestBody.setUrl(getUrl(serviceName, stubbyRequestBody));
    return mock.createStubbyRequest(stubbyRequestBody, stubbyResponseBody);
  }

  private String getUrl(String serviceName, RequestBody stubbyRequestBody) {
    return serviceName + stubbyRequestBody.getUrl();
  }
}
