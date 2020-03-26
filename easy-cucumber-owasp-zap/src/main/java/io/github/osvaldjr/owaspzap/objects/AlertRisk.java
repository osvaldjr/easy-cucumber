package io.github.osvaldjr.owaspzap.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlertRisk {

  private Integer low;
  private Integer medium;
  private Integer high;
  private Integer informational;
}
