package br.community.component.test.gateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.community.component.test.gateways.feign.TargetClient;

@Component
public class TargetGateway {

  @Autowired TargetClient feignClient;

  public Object post(String uri, Object body) {
    return feignClient.post(uri, body);
  }

  public Object get(String uri) {
    return feignClient.get(uri);
  }
}
