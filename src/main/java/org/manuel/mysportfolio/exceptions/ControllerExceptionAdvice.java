package org.manuel.mysportfolio.exceptions;

import javax.servlet.http.HttpServletRequest;
import org.manuel.mysportfolio.exceptions.model.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionAdvice {

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<AppError> handleNotFound(final EntityNotFoundException ex,
      final HttpServletRequest request) {
    final String bodyOfResponse = ex.getMessage();
    final AppError appError = new AppError("v1", "404", bodyOfResponse, ex.getDomain(),
        "Entity not found", bodyOfResponse, "errorReportUri");

    return new ResponseEntity<>(appError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
      final HttpServletRequest request) {
    final AppError appError = AppError.fromMethodArgumentNotValidException(ex);
    return new ResponseEntity<>(appError, HttpStatus.BAD_REQUEST);
  }

}
