package io.github.osvaldjr.domains;

import java.util.Collection;
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
public class ClientResponse {

  private int status;
  private String reason;
  private Map<String, Collection<String>> headers;
  private String jsonBody;
}
