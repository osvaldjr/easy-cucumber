package io.github.osvaldjr.unit.gateways.assemblers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import gherkin.deps.com.google.gson.Gson;
import io.github.glytching.junit.extension.random.Random;
import io.github.osvaldjr.domains.StubbyRequest;
import io.github.osvaldjr.gateways.stubby.assemblers.StubbyRequestAssembler;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyJsonRequest;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyRequestBody;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyResponseBody;
import io.github.osvaldjr.unit.UnitTest;

class StubbyRequestAssemblerTest extends UnitTest {

  @InjectMocks private StubbyRequestAssembler stubbyRequestAssembler;

  @Test
  void shouldAssembleRequestCorrectly(
      @Random StubbyRequest.RequestBody requestBody,
      @Random StubbyRequest.ResponseBody responseBody) {
    Gson gson = new Gson();

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
}
