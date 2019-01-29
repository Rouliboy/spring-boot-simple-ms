package org.jvi.demo.validation;

import java.time.Instant;
import lombok.Data;

@Data
public class ErrorDetails {
  private Instant timestamp;
  private String message;
  private String details;

  public ErrorDetails(final Instant timestamp, final String message, final String details) {
    super();
    this.timestamp = timestamp;
    this.message = message;
    this.details = details;
  }
}
