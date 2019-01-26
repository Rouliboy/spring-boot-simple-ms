package org.jvi.demo.model;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
@NoArgsConstructor(force = true)
public class Search {

  private String immId;

  @Valid
  private Params params;

  @Value
  @AllArgsConstructor
  @Builder
  @NoArgsConstructor(force = true)
  private static class Params {
    private String opener;
  }
}
