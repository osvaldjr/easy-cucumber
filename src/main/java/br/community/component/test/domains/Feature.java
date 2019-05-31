package br.community.component.test.domains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Feature {

  private String name;
  private FeatureStatus status;
}
