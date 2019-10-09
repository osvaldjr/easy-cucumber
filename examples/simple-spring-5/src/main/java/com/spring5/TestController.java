package com.spring5;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

  @Autowired MyFeignClient client;

  @GetMapping
  public Map test() {
    Map map = new HashMap();
    map.put("key", "value");
    client.get();
    return map;
  }
}
