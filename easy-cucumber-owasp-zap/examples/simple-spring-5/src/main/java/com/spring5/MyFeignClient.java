package com.spring5;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.cloud.openfeign.FeignClient(
    name = "client",
    url = "http://demo5951571.mockable.io")
public interface MyFeignClient {
  @GetMapping("/")
  Object get();
}
