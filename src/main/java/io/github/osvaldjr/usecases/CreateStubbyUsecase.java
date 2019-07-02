package io.github.osvaldjr.usecases;

import static io.github.osvaldjr.domains.StubbyRequest.RequestBody;
import static io.github.osvaldjr.domains.StubbyRequest.ResponseBody;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.gateways.FileGateway;
import io.github.osvaldjr.gateways.stubby.StubbyGateway;

@Component
public class CreateStubbyUsecase {

  private FileGateway fileGateway;
  private StubbyGateway stubbyGateway;

  @Autowired
  public CreateStubbyUsecase(FileGateway fileGateway, StubbyGateway stubbyGateway) {
    this.fileGateway = fileGateway;
    this.stubbyGateway = stubbyGateway;
  }

  public Integer execute(String scenario, String serviceName, String mockName) throws IOException {
    String mockRequestFile = "mocks/" + mockName + "-request";
    String mockResponseFile = "mocks/" + mockName + "-response";

    RequestBody stubbyRequestBody =
        fileGateway.getObjectFromFile(scenario, mockRequestFile, RequestBody.class);
    ResponseBody stubbyResponseBody =
        fileGateway.getObjectFromFile(scenario, mockResponseFile, ResponseBody.class);

    assertNotNull("url cannot be null in create mock", stubbyRequestBody.getUrl());
    assertNotNull("method cannot be null in create mock", stubbyRequestBody.getMethod());

    stubbyRequestBody.setUrl(getUrl(serviceName, stubbyRequestBody));
    return stubbyGateway.createStubbyRequest(stubbyRequestBody, stubbyResponseBody);
  }

  private String getUrl(String serviceName, RequestBody stubbyRequestBody) {
    return serviceName + stubbyRequestBody.getUrl();
  }
}
