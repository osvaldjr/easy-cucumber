package io.github.osvaldjr.utils;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
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

import io.github.osvaldjr.objects.TargetRequest;
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
    request.setHost(ofNullable(request.getHost()).orElse(targetHost));

    assertNotNull("url cannot be null in make request", request.getUrl());
    assertNotNull("method cannot be null in make request", request.getMethod());
    assertNotNull("host cannot be null in make request", request.getHost());

    ResponseEntity response;
    Map<String, String> headersMap = getMapOfNullable(request.getHeaders());
    Map<String, String> queryParametersMap = getMapOfNullable(request.getQueryParams());

    HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());

    switch (httpMethod) {
      case GET:
        response =
            targetGateway.get(request.getHost(), request.getUrl(), headersMap, queryParametersMap);
        break;
      case POST:
        response =
            targetGateway.post(
                request.getHost(),
                request.getUrl(),
                request.getBody(),
                headersMap,
                queryParametersMap);
        break;
      case PUT:
        response =
            targetGateway.put(
                request.getHost(),
                request.getUrl(),
                request.getBody(),
                headersMap,
                queryParametersMap);
        break;
      case DELETE:
        response =
            targetGateway.delete(
                request.getHost(),
                request.getUrl(),
                request.getBody(),
                headersMap,
                queryParametersMap);
        break;
      case PATCH:
        response =
            targetGateway.patch(
                request.getHost(),
                request.getUrl(),
                request.getBody(),
                headersMap,
                queryParametersMap);
        break;
      default:
        throw new MethodNotAllowedException(
            request.getMethod(), Arrays.asList(GET, POST, PUT, DELETE, PATCH));
    }
    return response;
  }

  private Map<String, String> getMapOfNullable(Map<String, String> headers) {
    return ofNullable(headers).orElseGet(HashMap::new);
  }
}
