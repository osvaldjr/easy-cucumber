package io.github.osvaldjr.mock.utils.stubby.jsons;

import java.io.Serializable;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StubbyRequestBody implements Serializable {

  private static final long serialVersionUID = -8161896987024165907L;
  private String url;
  private String method;
  private Object json;
  private Object post;
  private Map<String, String> headers;
  private Map<String, String> query;
}
