package component.test.gateways.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import component.test.gateways.stubby.jsons.StubbyRequest;
import component.test.gateways.stubby.jsons.StubbyResponse;
import feign.Param;
import feign.RequestLine;

@FeignClient(value = "stubby-client", url = "${stubby.url}")
public interface StubbyClient {

  @RequestLine("GET /")
  List<StubbyResponse> getAllServices();

  @RequestLine("POST /")
  ResponseEntity<Void> create(@RequestBody StubbyRequest request);

  @RequestLine("DELETE /{id}")
  void delete(@Param("id") Integer id);

  @RequestLine("GET /{id}")
  StubbyResponse getService(@Param("id") Integer id);
}
