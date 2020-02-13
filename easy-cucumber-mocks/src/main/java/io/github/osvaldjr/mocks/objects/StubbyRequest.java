package io.github.osvaldjr.mocks.objects;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StubbyRequest implements Serializable {

  private static final long serialVersionUID = 7394308360110765513L;
  private RequestBody request;
  private ResponseBody response;

  public enum BodyType {
    JSON,
    RAW
  }

  @Getter
  @Setter
  public static class RequestBody implements Serializable {

    private static final long serialVersionUID = -8161896987024165907L;
    private String url;
    private String method;
    private Object body;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> queryParams = new HashMap<>();
    private BodyType bodyType;
  }

  @Getter
  @Setter
  public static class ResponseBody implements Serializable {

    private static final long serialVersionUID = 2378274188837071302L;
    private Map<String, String> headers;
    private Integer status;
    private Object body;
    private BodyType bodyType;
  }
}
