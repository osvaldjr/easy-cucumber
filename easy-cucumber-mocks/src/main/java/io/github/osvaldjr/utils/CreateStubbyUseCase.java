package io.github.osvaldjr.utils;

import static io.github.osvaldjr.objects.StubbyRequest.RequestBody;
import static io.github.osvaldjr.objects.StubbyRequest.ResponseBody;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.gateways.FileGateway;
import io.github.osvaldjr.gateways.mock.MockGateway;

@Component
public class CreateStubbyUseCase {

  private FileGateway fileGateway;
  private MockGateway mockGateway;

  @Autowired
  public CreateStubbyUseCase(FileGateway fileGateway, MockGateway mockGateway) {
    this.fileGateway = fileGateway;
    this.mockGateway = mockGateway;
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
    return mockGateway.createStubbyRequest(stubbyRequestBody, stubbyResponseBody);
  }

  private String getUrl(String serviceName, RequestBody stubbyRequestBody) {
    return serviceName + stubbyRequestBody.getUrl();
  }
}
