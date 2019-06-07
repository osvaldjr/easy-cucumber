package br.community.component.test.usecases;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.community.component.test.gateways.FileGateway;
import br.community.component.test.gateways.stubby.StubbyGateway;
import br.community.component.test.gateways.stubby.jsons.StubbyRequestBody;
import br.community.component.test.gateways.stubby.jsons.StubbyResponseBody;

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

    StubbyRequestBody stubbyRequestBody =
        fileGateway.getObjectFromFile(scenario, mockRequestFile, StubbyRequestBody.class);
    StubbyResponseBody stubbyResponseBody =
        fileGateway.getObjectFromFile(scenario, mockResponseFile, StubbyResponseBody.class);

    stubbyRequestBody.setUrl(getUrl(serviceName, stubbyRequestBody));
    return stubbyGateway.createStubbyRequest(stubbyRequestBody, stubbyResponseBody);
  }

  private String getUrl(String serviceName, StubbyRequestBody stubbyRequestBody) {
    return serviceName + stubbyRequestBody.getUrl();
  }
}
