package io.github.osvaldjr.gateways.mock.stubby;

import static io.github.osvaldjr.objects.StubbyRequest.RequestBody;
import static io.github.osvaldjr.objects.StubbyRequest.ResponseBody;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.gateways.feign.StubbyClient;
import io.github.osvaldjr.gateways.mock.MockGateway;
import io.github.osvaldjr.gateways.mock.mockserver.MockServerMockGatewayImpl;
import io.github.osvaldjr.gateways.mock.stubby.assemblers.StubbyRequestAssembler;
import io.github.osvaldjr.gateways.mock.stubby.jsons.StubbyJsonRequest;
import io.github.osvaldjr.gateways.mock.stubby.jsons.StubbyJsonResponse;

@Component
@ConditionalOnMissingBean(MockServerMockGatewayImpl.class)
public class StubbyMockGatewayImpl implements MockGateway {

  private StubbyClient stubbyClient;
  private StubbyRequestAssembler stubbyRequestAssembler;

  @Autowired
  public StubbyMockGatewayImpl(
      StubbyClient stubbyClient, StubbyRequestAssembler stubbyRequestAssembler) {
    this.stubbyClient = stubbyClient;
    this.stubbyRequestAssembler = stubbyRequestAssembler;
  }

  @Override
  public String createStubbyRequest(RequestBody request, ResponseBody response) {
    StubbyJsonRequest stubbyJsonRequest = stubbyRequestAssembler.assemble(request, response);
    return getStubbyId(stubbyClient.create(stubbyJsonRequest));
  }

  @Override
  public void deleteAllServices() {
    List<StubbyJsonResponse> allServices = stubbyClient.getAllServices();
    if (CollectionUtils.isNotEmpty(allServices)) {
      allServices.forEach(service -> stubbyClient.delete(service.getId()));
    }
  }

  @Override
  public Integer getMockHits(Object id) {
    return stubbyClient.getService(Integer.valueOf(id.toString())).getHits();
  }

  private static String getStubbyId(ResponseEntity response) {
    String location = response.getHeaders().getFirst("location");
    Matcher matcher = Pattern.compile(".*?\\/(\\d+)").matcher(location);
    return matcher.find() ? matcher.group(1) : null;
  }
}
