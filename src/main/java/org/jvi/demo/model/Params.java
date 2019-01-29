package org.jvi.demo.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Builder
public class Params {

  @Size(max = 5)
  private String opener;

  @NotNull
  private SearchType searchType = SearchType.A;
}
