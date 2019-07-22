package io.github.osvaldjr.unit.gateways.mock.mockserver.assemblers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockserver.mock.Expectation;
import org.mockserver.model.Headers;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import gherkin.deps.com.google.gson.Gson;
import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.domains.StubbyRequest;
import io.github.osvaldjr.gateways.mock.mockserver.assemblers.ExpectationRequestAssembler;
import io.github.osvaldjr.unit.UnitTest;

class ExpectationRequestAssemblerTest extends UnitTest {

  @InjectMocks private ExpectationRequestAssembler expectationRequestAssembler;

  @Test
  void shouldAssembleCorrectly(
      @Random StubbyRequest.RequestBody requestBody,
      @Random StubbyRequest.ResponseBody responseBody,
      @Random int maxHits) {
    Gson gson = new Gson();
    Map<String, String> map = new HashMap<>();
    map.put("key", "value");
    requestBody.setBody(map);
    requestBody.setHeaders(map);

    Expectation expectation =
        expectationRequestAssembler.assemble(requestBody, responseBody, maxHits);

    assertThat(expectation, notNullValue());
    HttpRequest request = expectation.getHttpRequest();
    assertThat(request, notNullValue());

    assertHeaders(requestBody.getHeaders(), request.getHeaders());
    assertThat(request.getMethod(), equalTo(requestBody.getMethod()));
    assertThat(request.getBodyAsString(), equalTo(gson.toJson(requestBody.getBody())));
    assertThat(request.getPath(), equalTo("/" + requestBody.getUrl()));

    HttpResponse response = expectation.getHttpResponse();
    assertThat(response, notNullValue());

    assertHeaders(responseBody.getHeaders(), response.getHeaders());
    assertThat(response.getStatusCode(), equalTo(responseBody.getStatus()));
    assertThat(response.getBodyAsString(), equalTo(gson.toJson(responseBody.getBody())));
  }

  private void assertHeaders(Map<String, String> headersMap, Headers headers) {

    headersMap.forEach(
        (k, v) -> {
          assertTrue(headers.containsEntry(k));
          assertThat(headers.getFirstValue(k), equalTo(v));
        });
  }
}
