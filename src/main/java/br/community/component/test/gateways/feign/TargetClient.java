package br.community.component.test.gateways.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import feign.Body;
import feign.HeaderMap;
import feign.Param;
import feign.RequestLine;

@FeignClient(value = "target-client", url = "${target.url}")
public interface TargetClient {

  @RequestLine("GET /{uri}")
  ResponseEntity get(@Param("uri") String uri, @HeaderMap Map<String, String> headers);

  @RequestLine("POST /{uri}")
  @Body("{request}")
  <R> ResponseEntity post(@Param("uri") String uri, R body, @HeaderMap Map<String, String> headers);

  @RequestLine("DELETE /{uri}")
  @Body("{request}")
  <R> ResponseEntity delete(
      @Param("uri") String uri, R body, @HeaderMap Map<String, String> headers);

  @RequestLine("PUT/{uri}")
  @Body("{request}")
  <R> ResponseEntity put(@Param("uri") String uri, R body, @HeaderMap Map<String, String> headers);
}
