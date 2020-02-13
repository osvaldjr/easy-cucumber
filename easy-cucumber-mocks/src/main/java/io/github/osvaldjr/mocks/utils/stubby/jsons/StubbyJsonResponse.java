package io.github.osvaldjr.utils.stubby.jsons;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StubbyJsonResponse implements Serializable {

  private static final long serialVersionUID = -8674647371416791444L;
  private Integer hits;
  private Integer id;
}
