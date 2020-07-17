package io.github.osvaldjr.mock.utils.mockserver.assemblers;

import static org.mockserver.matchers.TimeToLive.unlimited;
import static org.mockserver.matchers.Times.exactly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mockserver.matchers.MatchType;
import org.mockserver.mock.Expectation;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.mockserver.model.JsonSchemaBody;
import org.mockserver.model.Parameter;
import org.mockserver.model.RegexBody;
import org.mockserver.model.StringBody;
import org.mockserver.model.XPathBody;
import org.springframework.stereotype.Component;

import gherkin.deps.com.google.gson.Gson;
import io.github.osvaldjr.mock.objects.StubbyRequest;

@Component
public class ExpectationRequestAssembler {

  private static final Gson gson = new Gson();

  public Expectation assemble(
      StubbyRequest.RequestBody request, StubbyRequest.ResponseBody response, int maxHits) {
    HttpRequest httpRequest = getHttpRequest(request);

    HttpResponse httpResponse =
        HttpResponse.response()
            .withBody(getResponseBody(response))
            .withStatusCode(response.getStatus())
            .withHeaders(getHeaders(response.getHeaders()));

    return new Expectation(httpRequest, exactly(maxHits), unlimited()).thenRespond(httpResponse);
  }

  private String getResponseBody(StubbyRequest.ResponseBody response) {
    return response.getBodyType() == StubbyRequest.BodyType.RAW
        ? response.getBody().toString()
        : gson.toJson(response.getBody());
  }

  private HttpRequest getHttpRequest(StubbyRequest.RequestBody request) {
    HttpRequest httpRequest = new HttpRequest();
    httpRequest
        .withMethod(request.getMethod())
        .withPath("/" + request.getUrl())
        .withHeaders(getHeaders(request.getHeaders()))
        .withQueryStringParameters(getQueryParameters(request.getQueryParams()));

    if (request.getBodyType() == StubbyRequest.BodyType.RAW) {
      httpRequest.withBody(request.getBody().toString());
    } else if (request.getBodyType() == StubbyRequest.BodyType.JSON_STRICT) {
      httpRequest.withBody(JsonBody.json(request.getBody(), MatchType.STRICT));
    } else if (request.getBodyType() == StubbyRequest.BodyType.JSON_ONLY_MATCHING_FIELDS) {
      httpRequest.withBody(JsonBody.json(request.getBody(), MatchType.ONLY_MATCHING_FIELDS));
    } else if (request.getBodyType() == StubbyRequest.BodyType.EXACT) {
      httpRequest.withBody(StringBody.exact(request.getBody().toString()));
    } else if (request.getBodyType() == StubbyRequest.BodyType.REGEX) {
      httpRequest.withBody(RegexBody.regex(request.getBody().toString()));
    } else if (request.getBodyType() == StubbyRequest.BodyType.JSON_SCHEMA) {
      httpRequest.withBody(JsonSchemaBody.jsonSchema(request.getBody().toString()));
    } else if (request.getBodyType() == StubbyRequest.BodyType.XPATH) {
      httpRequest.withBody(XPathBody.xpath(request.getBody().toString()));
    } else {
      httpRequest.withBody(gson.toJson(request.getBody()));
    }

    return httpRequest;
  }

  private List<Parameter> getQueryParameters(Map<String, String> queryParams) {
    List<Parameter> parameters = new ArrayList<>();
    queryParams.forEach((key, value) -> parameters.add(new Parameter(key, value)));
    return parameters;
  }

  private List<Header> getHeaders(Map<String, String> headersMap) {
    List<Header> headers = new ArrayList<>();
    headersMap.forEach((key, value) -> headers.add(new Header(key, value)));
    return headers;
  }
}
