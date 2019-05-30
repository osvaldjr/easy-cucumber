package br.community.component.test.gateways.feign;

import org.springframework.cloud.openfeign.FeignClient;

import feign.Param;
import feign.RequestLine;

@FeignClient(value = "target-client", url = "${target.url}")
public interface TargetClient {

  @RequestLine("GET /{uri}")
  Object get(@Param("uri") String uri);

  @RequestLine("POST /{uri}")
  Object post(@Param("uri") String uri, Object request);
}
