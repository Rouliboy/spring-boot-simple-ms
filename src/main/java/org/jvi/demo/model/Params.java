package org.jvi.demo.model;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
// @NoArgsConstructor(force = true)
public class Params {

  @Size(max = 5)
  private String opener;

  private SearchType searchType = SearchType.A;

  private Params() {
    opener = null;
  }
}
