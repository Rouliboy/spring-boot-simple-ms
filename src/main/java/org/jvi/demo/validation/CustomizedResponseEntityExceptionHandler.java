package org.jvi.demo.validation;

import java.time.Instant;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// @ControllerAdvice
// @RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status,
      final WebRequest request) {
    final ErrorDetails errorDetails =
        new ErrorDetails(Instant.now(), "Validation failed", ex.getBindingResult().toString());
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
}
