package org.jvi.demo.validation;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Kudos
 * http://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-adding-validation-to-a-rest-api/
 *
 */
// @Order(Ordered.HIGHEST_PRECEDENCE)
// @ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

  @ResponseStatus(BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Error methodArgumentNotValidException(final MethodArgumentNotValidException ex) {
    final BindingResult result = ex.getBindingResult();
    final List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
    return processFieldErrors(fieldErrors);
  }

  private Error processFieldErrors(
      final List<org.springframework.validation.FieldError> fieldErrors) {
    final Error error = new Error(BAD_REQUEST.value(), "validation error");
    for (final org.springframework.validation.FieldError fieldError : fieldErrors) {
      error.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
    }
    return error;
  }

  static class Error {
    private final int status;
    private final String message;
    private List<FieldError> fieldErrors = new ArrayList<>();

    Error(final int status, final String message) {
      this.status = status;
      this.message = message;
    }

    public int getStatus() {
      return status;
    }

    public String getMessage() {
      return message;
    }

    public void addFieldError(final String path, final String message) {
      final FieldError error = new FieldError(path, message, message);
      fieldErrors.add(error);
    }

    public List<FieldError> getFieldErrors() {
      return fieldErrors;
    }
  }
}
