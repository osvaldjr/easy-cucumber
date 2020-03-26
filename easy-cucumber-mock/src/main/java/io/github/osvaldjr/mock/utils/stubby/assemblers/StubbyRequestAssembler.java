package io.github.osvaldjr.mock.utils.stubby.assemblers;

import org.springframework.stereotype.Component;

import gherkin.deps.com.google.gson.Gson;
import io.github.osvaldjr.mock.objects.StubbyRequest;
import io.github.osvaldjr.mock.utils.stubby.jsons.StubbyJsonRequest;
import io.github.osvaldjr.mock.utils.stubby.jsons.StubbyRequestBody;
import io.github.osvaldjr.mock.utils.stubby.jsons.StubbyResponseBody;

@Component
public class StubbyRequestAssembler {

  private static final Gson gson = new Gson();

  public StubbyJsonRequest assemble(
      StubbyRequest.RequestBody stubbyRequestBody, StubbyRequest.ResponseBody stubbyResponseBody) {
    return StubbyJsonRequest.builder()
        .request(buildRequest(stubbyRequestBody))
        .response(buildResponseBody(stubbyResponseBody))
        .build();
  }

  private StubbyResponseBody buildResponseBody(StubbyRequest.ResponseBody stubbyResponseBody) {
    return StubbyResponseBody.builder()
        .body(stubbyResponseBody.getBody())
        .headers(stubbyResponseBody.getHeaders())
        .status(stubbyResponseBody.getStatus())
        .build();
  }

  private StubbyRequestBody buildRequest(StubbyRequest.RequestBody stubbyRequestBody) {

    StubbyRequestBody.StubbyRequestBodyBuilder builder =
        StubbyRequestBody.builder()
            .headers(stubbyRequestBody.getHeaders())
            .method(stubbyRequestBody.getMethod())
            .query(stubbyRequestBody.getQueryParams())
            .url(stubbyRequestBody.getUrl());
    if (StubbyRequest.BodyType.RAW == stubbyRequestBody.getBodyType()) {
      builder.post(stubbyRequestBody.getBody().toString());
    } else {
      builder.json(gson.toJson(stubbyRequestBody.getBody()));
    }

    return builder.build();
  }
}
