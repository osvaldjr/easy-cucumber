package io.github.osvaldjr.gateways.stubby;

import static io.github.osvaldjr.domains.StubbyRequest.RequestBody;
import static io.github.osvaldjr.domains.StubbyRequest.ResponseBody;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.domains.StubbyResponse;
import io.github.osvaldjr.gateways.feign.StubbyClient;
import io.github.osvaldjr.gateways.stubby.assemblers.StubbyRequestAssembler;
import io.github.osvaldjr.gateways.stubby.assemblers.StubbyResponseAssembler;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyJsonRequest;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyJsonResponse;

@Component
public class StubbyGateway {

  private StubbyClient stubbyClient;
  private StubbyRequestAssembler stubbyRequestAssembler;
  private StubbyResponseAssembler stubbyResponseAssembler;

  @Autowired
  public StubbyGateway(
      StubbyClient stubbyClient,
      StubbyRequestAssembler stubbyRequestAssembler,
      StubbyResponseAssembler stubbyResponseAssembler) {
    this.stubbyClient = stubbyClient;
    this.stubbyRequestAssembler = stubbyRequestAssembler;
    this.stubbyResponseAssembler = stubbyResponseAssembler;
  }

  public Integer createStubbyRequest(RequestBody request, ResponseBody response) {
    StubbyJsonRequest stubbyJsonRequest = stubbyRequestAssembler.assemble(request, response);
    return getStubbyId(stubbyClient.create(stubbyJsonRequest));
  }

  public void deleteAllServices() {
    List<StubbyJsonResponse> allServices = stubbyClient.getAllServices();
    if (CollectionUtils.isNotEmpty(allServices)) {
      allServices.forEach(service -> stubbyClient.delete(service.getId()));
    }
  }

  public StubbyResponse getStubbyResponse(Integer id) {
    return stubbyResponseAssembler.assemble(stubbyClient.getService(id));
  }

  private static Integer getStubbyId(ResponseEntity response) {
    String location = response.getHeaders().getFirst("location");
    Matcher matcher = Pattern.compile(".*?\\/(\\d+)").matcher(location);
    return matcher.find() ? Integer.parseInt(matcher.group(1)) : null;
  }
}
