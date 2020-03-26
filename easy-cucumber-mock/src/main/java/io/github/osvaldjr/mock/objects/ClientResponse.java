package io.github.osvaldjr.mock.objects;

import java.io.Serializable;
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
public class ClientResponse implements Serializable {

  private static final long serialVersionUID = -5160573693103964205L;
  private int status;
  private String reason;
  private Map<String, Collection<String>> headers;
  private String jsonBody;
}
