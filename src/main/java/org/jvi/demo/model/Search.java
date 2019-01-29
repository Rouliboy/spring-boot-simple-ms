package org.jvi.demo.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.jvi.demo.model.pagination.Pagination;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Builder
public class Search {

  @Valid
  @NotNull
  private Body body;

  @Valid
  private Params params = Params.builder().build();

  @Valid
  @NotNull
  private Pagination pagination;
}
