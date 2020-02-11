package io.github.osvaldjr.unit.gateways.mock.stubby.assemblers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import gherkin.deps.com.google.gson.Gson;
import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.domains.StubbyRequest;
import io.github.osvaldjr.gateways.mock.stubby.assemblers.StubbyRequestAssembler;
import io.github.osvaldjr.gateways.mock.stubby.jsons.StubbyJsonRequest;
import io.github.osvaldjr.gateways.mock.stubby.jsons.StubbyRequestBody;
import io.github.osvaldjr.gateways.mock.stubby.jsons.StubbyResponseBody;
import io.github.osvaldjr.unit.UnitTest;

class StubbyRequestAssemblerTest extends UnitTest {

  @InjectMocks private StubbyRequestAssembler stubbyRequestAssembler;

  @Test
  void shouldAssembleRequestCorrectly(
      @Random StubbyRequest.RequestBody requestBody,
      @Random StubbyRequest.ResponseBody responseBody) {
    Gson gson = new Gson();
    Map<String, String> map = new HashMap<>();
    map.put("key", "value");
    requestBody.setBody(map);
    requestBody.setBodyType(StubbyRequest.BodyType.JSON);

    StubbyJsonRequest stubbyJsonRequest =
        stubbyRequestAssembler.assemble(requestBody, responseBody);

    assertThat(stubbyJsonRequest, notNullValue());
    StubbyRequestBody request = stubbyJsonRequest.getRequest();
    assertThat(request, notNullValue());
    assertThat(request.getHeaders(), equalTo(requestBody.getHeaders()));
    assertThat(request.getMethod(), equalTo(requestBody.getMethod()));
    assertThat(request.getQuery(), equalTo(requestBody.getQueryParams()));
    assertThat(request.getUrl(), equalTo(requestBody.getUrl()));
    assertThat(request.getJson(), equalTo(gson.toJson(requestBody.getBody())));
    assertThat(request.getPost(), nullValue());

    StubbyResponseBody response = stubbyJsonRequest.getResponse();
    assertThat(response, notNullValue());
    assertThat(response.getHeaders(), equalTo(responseBody.getHeaders()));
    assertThat(response.getStatus(), equalTo(responseBody.getStatus()));
    assertThat(response.getBody(), equalTo(responseBody.getBody()));
  }

  @Test
  void shouldAssembleRequestCorrectlyWithPlainText(
      @Random StubbyRequest.RequestBody requestBody,
      @Random StubbyRequest.ResponseBody responseBody) {
    requestBody.setBodyType(StubbyRequest.BodyType.RAW);

    StubbyJsonRequest stubbyJsonRequest =
        stubbyRequestAssembler.assemble(requestBody, responseBody);

    assertThat(stubbyJsonRequest, notNullValue());
    StubbyRequestBody request = stubbyJsonRequest.getRequest();
    assertThat(request, notNullValue());
    assertThat(request.getHeaders(), equalTo(requestBody.getHeaders()));
    assertThat(request.getMethod(), equalTo(requestBody.getMethod()));
    assertThat(request.getQuery(), equalTo(requestBody.getQueryParams()));
    assertThat(request.getUrl(), equalTo(requestBody.getUrl()));
    assertThat(request.getJson(), nullValue());
    assertThat(request.getPost().toString(), equalTo(requestBody.getBody().toString()));
    StubbyResponseBody response = stubbyJsonRequest.getResponse();
    assertThat(response, notNullValue());
    assertThat(response.getHeaders(), equalTo(responseBody.getHeaders()));
    assertThat(response.getStatus(), equalTo(responseBody.getStatus()));
    assertThat(response.getBody(), equalTo(responseBody.getBody()));
  }
}
