package io.github.osvaldjr.core.utils;

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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.MethodNotAllowedException;

import io.github.osvaldjr.core.objects.TargetRequest;
import io.github.osvaldjr.core.objects.properties.ApplicationProperties;

@Component
public class RequestHttpTarget {

  private final HttpTarget httpTarget;
  private final ApplicationProperties applicationProperties;

  @Autowired
  public RequestHttpTarget(HttpTarget httpTarget, ApplicationProperties applicationProperties) {
    this.httpTarget = httpTarget;
    this.applicationProperties = applicationProperties;
  }

  public ResponseEntity execute(TargetRequest request) {
    request.setHost(
        ofNullable(request.getHost()).orElse(applicationProperties.getTarget().getUrl()));

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
            httpTarget.get(request.getHost(), request.getUrl(), headersMap, queryParametersMap);
        break;
      case POST:
        response =
            httpTarget.post(
                request.getHost(),
                request.getUrl(),
                request.getBody(),
                headersMap,
                queryParametersMap);
        break;
      case PUT:
        response =
            httpTarget.put(
                request.getHost(),
                request.getUrl(),
                request.getBody(),
                headersMap,
                queryParametersMap);
        break;
      case DELETE:
        response =
            httpTarget.delete(
                request.getHost(),
                request.getUrl(),
                request.getBody(),
                headersMap,
                queryParametersMap);
        break;
      case PATCH:
        response =
            httpTarget.patch(
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
