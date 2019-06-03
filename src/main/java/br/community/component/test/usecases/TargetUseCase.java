package br.community.component.test.usecases;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.MethodNotAllowedException;

import br.community.component.test.domains.TargetRequest;
import br.community.component.test.gateways.TargetGateway;

@Component
public class TargetUseCase {

  @Autowired TargetGateway targetGateway;

  public ResponseEntity request(TargetRequest request) {
    ResponseEntity response;
    Map<String, String> headersMap = getHeaders(request.getHeaders());
    HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
    switch (httpMethod) {
      case GET:
        response = targetGateway.get(request.getUri(), headersMap);
        break;
      case POST:
        response = targetGateway.post(request.getUri(), request.getBody(), headersMap);
        break;
      case PUT:
        response = targetGateway.put(request.getUri(), request.getBody(), headersMap);
        break;
      case DELETE:
        response = targetGateway.delete(request.getUri(), request.getBody(), headersMap);
        break;
      default:
        throw new MethodNotAllowedException(
            request.getMethod(), Arrays.asList(GET, POST, PUT, DELETE));
    }
    return response;
  }

  private Map<String, String> getHeaders(Map<String, String> headers) {
    return ofNullable(headers).orElseGet(HashMap::new);
  }
}
