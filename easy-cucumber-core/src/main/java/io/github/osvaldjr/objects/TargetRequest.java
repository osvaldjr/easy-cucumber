package io.github.osvaldjr.objects;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

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
