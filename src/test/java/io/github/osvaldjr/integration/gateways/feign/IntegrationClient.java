package io.github.osvaldjr.integration.gateways.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import feign.Body;
import feign.HeaderMap;
import feign.RequestLine;

@FeignClient(name = "integration", url = "${dependencies.integration.url}")
public interface IntegrationClient {

  @RequestLine("GET /get")
  ResponseEntity<Object> get(@HeaderMap Map<String, String> headers);

  @RequestLine("PUT /put")
  @Body("{body}")
  ResponseEntity<Object> put(Object body, @HeaderMap Map<String, String> headers);

  @RequestLine("POST /post")
  @Body("{body}")
  ResponseEntity<Object> post(Object body, @HeaderMap Map<String, String> headers);

  @RequestLine("DELETE /delete")
  @Body("{body}")
  ResponseEntity<Object> delete(Object body, @HeaderMap Map<String, String> headers);
}
