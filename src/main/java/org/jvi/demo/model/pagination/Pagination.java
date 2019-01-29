package org.jvi.demo.model.pagination;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Builder
public class Pagination {

  @Min(0)
  @NotNull
  @JsonProperty(required = true)
  private int pageNumber;

  @Min(0)
  @NotNull
  @JsonProperty(required = true)
  private int numberOfEls;

  @Singular
  @Valid
  private List<OrderBy> orderBys = new ArrayList<>();

}
