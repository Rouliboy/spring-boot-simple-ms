package org.jvi.demo.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class Search {

  @NotNull
  @Size(max = 10, min = 1)
  private String immId;

  @Valid
  private Params params;

  private Search() {
    immId = null;
    params = new Params(null);
  }
}
