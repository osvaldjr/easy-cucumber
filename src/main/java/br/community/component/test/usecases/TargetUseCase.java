package br.community.component.test.usecases;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.util.Arrays;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.MethodNotAllowedException;

import br.community.component.test.gateways.TargetGateway;

@Component
public class TargetUseCase {

  @Autowired TargetGateway targetGateway;

  public Object request(String method, String uri, Object body) {
    Object response;
    HttpMethod httpMethod = HttpMethod.valueOf(method);
    switch (httpMethod) {
      case GET:
        response = targetGateway.get(uri);
        break;
      case POST:
        response = targetGateway.post(uri, body);
        break;
      case PUT:
        throw new NotImplementedException("method not implemented");
      case DELETE:
        throw new NotImplementedException("method not implemented");
      default:
        throw new MethodNotAllowedException(method, Arrays.asList(GET, POST, PUT, DELETE));
    }
    return response;
  }
}
