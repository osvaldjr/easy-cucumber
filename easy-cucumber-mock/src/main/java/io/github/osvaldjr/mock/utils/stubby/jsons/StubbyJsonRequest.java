package io.github.osvaldjr.mock.utils.stubby.jsons;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StubbyJsonRequest implements Serializable {

  private static final long serialVersionUID = 7394308360110765513L;
  private StubbyRequestBody request;
  private StubbyResponseBody response;
}
