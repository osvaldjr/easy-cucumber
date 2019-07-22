package io.github.osvaldjr.gateways.mock.mockserver.assemblers;

import static org.mockserver.matchers.TimeToLive.unlimited;
import static org.mockserver.matchers.Times.exactly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mockserver.mock.Expectation;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.stereotype.Component;

import gherkin.deps.com.google.gson.Gson;
import io.github.osvaldjr.domains.StubbyRequest;

@Component
public class ExpectationRequestAssembler {

  private static final Gson gson = new Gson();

  public Expectation assemble(
      StubbyRequest.RequestBody request, StubbyRequest.ResponseBody response, int maxHits) {
    HttpRequest httpRequest = getHttpRequest(request);

    HttpResponse httpResponse =
        HttpResponse.response()
            .withBody(gson.toJson(response.getBody()))
            .withStatusCode(response.getStatus())
            .withHeaders(getHeaders(response.getHeaders()));

    return new Expectation(httpRequest, exactly(maxHits), unlimited()).thenRespond(httpResponse);
  }

  private HttpRequest getHttpRequest(StubbyRequest.RequestBody request) {
    HttpRequest httpRequest = new HttpRequest();
    httpRequest
        .withMethod(request.getMethod())
        .withPath("/" + request.getUrl())
        .withHeaders(getHeaders(request.getHeaders()))
        .withBody(gson.toJson(request.getBody()));
    return httpRequest;
  }

  private List<Header> getHeaders(Map<String, String> headersMap) {
    List<Header> headers = new ArrayList<>();
    headersMap.forEach((key, value) -> headers.add(new Header(key, value)));
    return headers;
  }
}
