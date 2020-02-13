package io.github.osvaldjr.utils.stubby.jsons;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class StubbyJsonRequest implements Serializable {

  private static final long serialVersionUID = 7394308360110765513L;
  private StubbyRequestBody request;
  private StubbyResponseBody response;
}
