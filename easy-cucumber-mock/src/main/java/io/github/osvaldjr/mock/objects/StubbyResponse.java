package io.github.osvaldjr.mock.objects;

import java.io.Serializable;

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
public class StubbyResponse implements Serializable {

  private static final long serialVersionUID = -8674647371416791444L;
  private Integer hits;
  private Integer id;
}
