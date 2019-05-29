package component.test.usecases;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import component.test.gateways.file.FileGateway;
import component.test.gateways.stubby.StubbyGateway;
import component.test.gateways.stubby.jsons.StubbyRequestBody;
import component.test.gateways.stubby.jsons.StubbyResponse;
import component.test.gateways.stubby.jsons.StubbyResponseBody;

@Component
public class StubbyUsecase {

  private FileGateway fileGateway;
  private StubbyGateway stubbyGateway;
  private Map<String, Integer> stubbyIdMap;

  @Autowired
  public StubbyUsecase(FileGateway fileGateway, StubbyGateway stubbyGateway) {
    this.fileGateway = fileGateway;
    this.stubbyGateway = stubbyGateway;
    this.stubbyIdMap = new HashMap<>();
  }

  public void execute(String scenario, String serviceName, String mockName) throws IOException {
    String mockRequestFile = "mocks/" + mockName + "-request";
    String mockResponseFile = "mocks/" + mockName + "-response";

    StubbyRequestBody stubbyRequestBody =
        fileGateway.getObjectFromFile(scenario, mockRequestFile, StubbyRequestBody.class);
    StubbyResponseBody stubbyResponseBody =
        fileGateway.getObjectFromFile(scenario, mockResponseFile, StubbyResponseBody.class);

    stubbyRequestBody.setUrl(getUrl(serviceName, stubbyRequestBody));
    Integer stubbyId = stubbyGateway.createStubbyRequest(stubbyRequestBody, stubbyResponseBody);

    String mapKey = getStubbyKey(scenario, serviceName, mockName);
    stubbyIdMap.put(mapKey, stubbyId);
  }

  public Integer getMockHits(String scenario, String serviceName, String mockName) {
    String mapKey = getStubbyKey(scenario, serviceName, mockName);
    Integer stubbyId = stubbyIdMap.get(mapKey);
    StubbyResponse stubbyResponse = stubbyGateway.getStubbyResponse(stubbyId);
    return stubbyResponse.getHits();
  }

  private String getStubbyKey(String scenario, String serviceName, String mockName) {
    return scenario + serviceName + mockName;
  }

  private String getUrl(String serviceName, StubbyRequestBody stubbyRequestBody) {
    return serviceName + stubbyRequestBody.getUrl();
  }
}
