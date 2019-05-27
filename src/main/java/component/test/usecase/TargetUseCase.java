package component.test.usecase;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.util.Arrays;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.MethodNotAllowedException;

import component.test.gateway.TargetGateway;

@Component
public class TargetUseCase {

  @Autowired TargetGateway targetGateway;

  public Object request(String method, String uri, Object body) {
    Object response;
    switch (method) {
      case "GET":
        response = targetGateway.get(uri);
        break;
      case "POST":
        response = targetGateway.post(uri, body);
        break;
      case "PUT":
        throw new NotImplementedException("method not implemented");
      case "DELETE":
        throw new NotImplementedException("method not implemented");
      default:
        throw new MethodNotAllowedException(method, Arrays.asList(GET, POST, PUT, DELETE));
    }
    return response;
  }
}
