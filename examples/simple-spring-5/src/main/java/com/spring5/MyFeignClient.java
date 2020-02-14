package com.spring5;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.cloud.openfeign.FeignClient(
    name = "client",
    url = "http://www.mocky.io/v2/5e45febe3000007224614fa2")
public interface MyFeignClient {
  @GetMapping("/")
  Object get();
}
