package io.github.osvaldjr.mock;

import io.github.osvaldjr.mock.stubby.jsons.StubbyJsonRequest;
import io.github.osvaldjr.mock.stubby.jsons.StubbyJsonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "stubby-client", url = "${dependencies.stubby.url:}")
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
