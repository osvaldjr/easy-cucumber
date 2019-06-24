package io.github.osvaldjr.gateways.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import feign.Param;
import feign.RequestLine;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyRequest;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyResponse;

@FeignClient(value = "stubby-client", url = "${dependencies.stubby.url:}")
public interface StubbyClient {

  @RequestLine("GET /")
  List<StubbyResponse> getAllServices();

  @RequestLine("POST /")
  ResponseEntity<StubbyResponse> create(@RequestBody StubbyRequest request);

  @RequestLine("DELETE /{id}")
  void delete(@Param("id") Integer id);

  @RequestLine("GET /{id}")
  StubbyResponse getService(@Param("id") Integer id);
}
