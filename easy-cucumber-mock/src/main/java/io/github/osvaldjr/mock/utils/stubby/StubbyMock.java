package io.github.osvaldjr.mock.utils.stubby;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import feign.Feign;
import io.github.osvaldjr.core.configs.FeignBuilder;
import io.github.osvaldjr.core.objects.properties.ApplicationProperties;
import io.github.osvaldjr.core.objects.properties.Stubby4NodeProperties;
import io.github.osvaldjr.mock.clients.StubbyClient;
import io.github.osvaldjr.mock.objects.StubbyRequest;
import io.github.osvaldjr.mock.utils.Mock;
import io.github.osvaldjr.mock.utils.mockserver.MockServerMock;
import io.github.osvaldjr.mock.utils.stubby.assemblers.StubbyRequestAssembler;
import io.github.osvaldjr.mock.utils.stubby.jsons.StubbyJsonRequest;
import io.github.osvaldjr.mock.utils.stubby.jsons.StubbyJsonResponse;

@Component
@ConditionalOnMissingBean(MockServerMock.class)
public class StubbyMock implements Mock {

  private Feign.Builder feignBuilder;
  private final StubbyRequestAssembler stubbyRequestAssembler;
  private final ApplicationProperties applicationProperties;

  @Autowired
  public StubbyMock(
      StubbyRequestAssembler stubbyRequestAssembler, ApplicationProperties applicationProperties) {
    this.stubbyRequestAssembler = stubbyRequestAssembler;
    this.applicationProperties = applicationProperties;
    this.feignBuilder = FeignBuilder.getClient();
  }

  private static String getStubbyId(ResponseEntity response) {
    String location = response.getHeaders().getFirst("location");
    Matcher matcher = Pattern.compile(".*?\\/(\\d+)").matcher(location);
    return matcher.find() ? matcher.group(1) : null;
  }

  @Override
  public String createStubbyRequest(
      StubbyRequest.RequestBody request, StubbyRequest.ResponseBody response) {
    StubbyJsonRequest stubbyJsonRequest = stubbyRequestAssembler.assemble(request, response);
    return getStubbyId(buildClient().create(stubbyJsonRequest));
  }

  @Override
  public void deleteAllServices() {
    List<StubbyJsonResponse> allServices = buildClient().getAllServices();
    if (CollectionUtils.isNotEmpty(allServices)) {
      allServices.forEach(service -> buildClient().delete(service.getId()));
    }
  }

  @Override
  public Integer getMockHits(Object id) {
    return buildClient().getService(Integer.valueOf(id.toString())).getHits();
  }

  private StubbyClient buildClient() {
    Stubby4NodeProperties stubby4Node = applicationProperties.getDependencies().getStubby();
    return feignBuilder.target(StubbyClient.class, stubby4Node.getUrl());
  }
}
