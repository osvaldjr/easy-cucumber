package io.github.osvaldjr.gateways.stubby.assemblers;

import static io.github.osvaldjr.domains.StubbyRequest.RequestBody;
import static io.github.osvaldjr.domains.StubbyRequest.ResponseBody;

import org.springframework.stereotype.Component;

import gherkin.deps.com.google.gson.Gson;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyJsonRequest;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyRequestBody;
import io.github.osvaldjr.gateways.stubby.jsons.StubbyResponseBody;

@Component
public class StubbyRequestAssembler {

  private static final Gson gson = new Gson();

  public StubbyJsonRequest assemble(
      RequestBody stubbyRequestBody, ResponseBody stubbyResponseBody) {
    return StubbyJsonRequest.builder()
        .request(buildRequest(stubbyRequestBody))
        .response(buildResponseBody(stubbyResponseBody))
        .build();
  }

  private StubbyResponseBody buildResponseBody(ResponseBody stubbyResponseBody) {
    return StubbyResponseBody.builder()
        .body(stubbyResponseBody.getBody())
        .headers(stubbyResponseBody.getHeaders())
        .status(stubbyResponseBody.getStatus())
        .build();
  }

  private StubbyRequestBody buildRequest(RequestBody stubbyRequestBody) {
    return StubbyRequestBody.builder()
        .headers(stubbyRequestBody.getHeaders())
        .json(gson.toJson(stubbyRequestBody.getBody()))
        .method(stubbyRequestBody.getMethod())
        .query(stubbyRequestBody.getQueryParams())
        .url(stubbyRequestBody.getUrl())
        .build();
  }
}
