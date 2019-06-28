package io.github.osvaldjr.gateways.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import feign.Param;
import feign.RequestLine;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyJsonRequest;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyJsonResponse;

@FeignClient(value = "stubby-client", url = "${dependencies.stubby.url:}")
public interface StubbyClient {

  @RequestLine("GET /")
  List<StubbyJsonResponse> getAllServices();

  @RequestLine("POST /")
  ResponseEntity<StubbyJsonResponse> create(@RequestBody StubbyJsonRequest request);

  @RequestLine("DELETE /{id}")
  void delete(@Param("id") Integer id);

  @RequestLine("GET /{id}")
  StubbyJsonResponse getService(@Param("id") Integer id);
}
