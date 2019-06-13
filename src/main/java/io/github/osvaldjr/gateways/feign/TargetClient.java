package io.github.osvaldjr.gateways.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import feign.Body;
import feign.HeaderMap;
import feign.Param;
import feign.RequestLine;

@FeignClient(value = "target-client", url = "${target.url}")
public interface TargetClient {

  @RequestLine("GET /{url}")
  ResponseEntity<?> get(@Param("url") String uri, @HeaderMap Map<String, String> headers);

  @RequestLine("POST /{url}")
  @Body("{request}")
  <R> ResponseEntity<?> post(
      @Param("url") String uri, R body, @HeaderMap Map<String, String> headers);

  @RequestLine("DELETE /{url}")
  @Body("{request}")
  <R> ResponseEntity<?> delete(
      @Param("url") String uri, R body, @HeaderMap Map<String, String> headers);

  @RequestLine("PUT/{url}")
  @Body("{request}")
  <R> ResponseEntity<?> put(
      @Param("url") String uri, R body, @HeaderMap Map<String, String> headers);
}