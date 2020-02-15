package io.github.osvaldjr.core.objects;

import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class TargetRequest<T> {

  private String host;
  private String method;
  private String url;
  private T body;
  private Map<String, String> headers;
  private Map<String, String> queryParams;
}
