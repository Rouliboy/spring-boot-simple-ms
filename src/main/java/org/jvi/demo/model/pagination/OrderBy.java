package org.jvi.demo.model.pagination;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Builder
public class OrderBy {

  @NotNull
  private String field;

  private Direction direction = Direction.ASC;

  public enum Direction {
    ASC, DESC;
  }
}
