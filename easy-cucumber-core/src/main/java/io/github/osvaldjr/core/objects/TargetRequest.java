package io.github.osvaldjr.core.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class TargetRequest<T> {

  private String host;
  private String method;
  private String url;
  private T body;
  private Map<String, String> headers;
  private Map<String, String> queryParams;
}
