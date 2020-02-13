package io.github.osvaldjr.mock.stubby.assemblers;

import gherkin.deps.com.google.gson.Gson;
import io.github.osvaldjr.mock.stubby.jsons.StubbyJsonRequest;
import io.github.osvaldjr.mock.stubby.jsons.StubbyRequestBody;
import io.github.osvaldjr.mock.stubby.jsons.StubbyResponseBody;
import io.github.osvaldjr.objects.StubbyRequest;
import org.springframework.stereotype.Component;

import static io.github.osvaldjr.objects.StubbyRequest.RequestBody;
import static io.github.osvaldjr.objects.StubbyRequest.ResponseBody;

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
