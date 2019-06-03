package br.community.component.test.domains;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TargetRequest<T> {
  private String method;
  private String uri;
  private T body;
  private Map<String, String> headers;
}
