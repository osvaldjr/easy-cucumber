package io.github.osvaldjr.usecases;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.MethodNotAllowedException;

import io.github.osvaldjr.domains.TargetRequest;
import io.github.osvaldjr.gateways.TargetGateway;

@Component
public class RequestTargetUseCase {

  private final TargetGateway targetGateway;

  @Value("${target.url:}")
  private String targetHost;

  @Autowired
  public RequestTargetUseCase(TargetGateway targetGateway) {
    this.targetGateway = targetGateway;
  }

  public ResponseEntity execute(TargetRequest request) {
    ResponseEntity response;
    Map<String, String> headersMap = getHeaders(request.getHeaders());
    HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
    String host = ofNullable(request.getHost()).orElse(targetHost);
    request.setHost(host);
    switch (httpMethod) {
      case GET:
        response = targetGateway.get(request.getHost(), request.getUrl(), headersMap);
        break;
      case POST:
        response =
            targetGateway.post(request.getHost(), request.getUrl(), request.getBody(), headersMap);
        break;
      case PUT:
        response =
            targetGateway.put(request.getHost(), request.getUrl(), request.getBody(), headersMap);
        break;
      case DELETE:
        response =
            targetGateway.delete(
                request.getHost(), request.getUrl(), request.getBody(), headersMap);
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
