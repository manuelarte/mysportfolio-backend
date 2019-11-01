package org.manuel.mysportfolio.exceptions;

import org.manuel.mysportfolio.exceptions.model.AppError;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionAdvice  {

    @ExceptionHandler(EntityNotFoundException.class)
    // TODO create error pojo
    protected ResponseEntity<AppError> handleNotFound(final EntityNotFoundException ex,
                                                      final HttpServletRequest request) {
        final String bodyOfResponse = ex.getMessage();

        final AppError appError = new AppError("v1",  "404", bodyOfResponse, ex.getDomain(),
        "Entity not found", bodyOfResponse, "errorReportUri");

        return new ResponseEntity<>(appError, HttpStatus.NOT_FOUND);
    }

}
