package io.github.osvaldjr.gateways.stubby;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.gateways.feign.StubbyClient;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyRequest;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyRequestBody;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyResponse;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyResponseBody;

@Component
public class StubbyGateway {

  private StubbyClient stubbyClient;

  @Autowired
  public StubbyGateway(StubbyClient stubbyClient) {
    this.stubbyClient = stubbyClient;
  }

  public Integer createStubbyRequest(StubbyRequestBody request, StubbyResponseBody response) {
    StubbyRequest stubbyRequest =
        StubbyRequest.builder().request(request).response(response).build();
    return getStubbyId(stubbyClient.create(stubbyRequest));
  }

  public void deleteAllServices() {
    List<StubbyResponse> allServices = stubbyClient.getAllServices();
    if (CollectionUtils.isNotEmpty(allServices)) {
      allServices.forEach(service -> stubbyClient.delete(service.getId()));
    }
  }

  public StubbyResponse getStubbyResponse(Integer id) {
    return stubbyClient.getService(id);
  }

  private static Integer getStubbyId(ResponseEntity response) {
    String location = response.getHeaders().getFirst("location");
    Matcher matcher = Pattern.compile(".*?\\/(\\d+)").matcher(location);
    return matcher.find() ? Integer.parseInt(matcher.group(1)) : null;
  }
}
