package br.community.component.test.gateways.stubby.jsons;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StubbyRequestBody implements Serializable {

  private static final long serialVersionUID = -8161896987024165907L;
  private String url;
  private String method;
  private Object body;
  private Map<String, String> headers = new HashMap<>();
  private Map<String, String> queryParams = new HashMap<>();
}
