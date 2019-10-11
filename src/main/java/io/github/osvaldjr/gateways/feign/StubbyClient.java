package io.github.osvaldjr.gateways.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.github.osvaldjr.gateways.mock.stubby.jsons.StubbyJsonRequest;
import io.github.osvaldjr.gateways.mock.stubby.jsons.StubbyJsonResponse;

@FeignClient(value = "stubby-client", url = "${dependencies.stubby.url:}")
@Component
public interface StubbyClient {

  @GetMapping
  List<StubbyJsonResponse> getAllServices();

  @PostMapping
  ResponseEntity<StubbyJsonResponse> create(@RequestBody StubbyJsonRequest request);

  @DeleteMapping(path = "/{id}")
  void delete(@PathVariable("id") Integer id);

  @GetMapping(path = "/{id}")
  StubbyJsonResponse getService(@PathVariable("id") Integer id);
}
