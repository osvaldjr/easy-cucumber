package io.github.osvaldjr.mocks.utils.stubby.jsons;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@Builder
public class StubbyResponseBody implements Serializable {

  private static final long serialVersionUID = 2378274188837071302L;
  private Map<String, String> headers;
  private Integer status;
  private Object body;
}
