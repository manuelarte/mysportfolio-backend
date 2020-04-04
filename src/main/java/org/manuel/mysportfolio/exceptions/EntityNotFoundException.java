package org.manuel.mysportfolio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
@lombok.Data
@lombok.EqualsAndHashCode(callSuper=false)
public class EntityNotFoundException extends RuntimeException {

  private String domain = null;

  public EntityNotFoundException(final Class<?> clazz, final String id) {
    this(clazz.getSimpleName(), id);
  }

  public EntityNotFoundException(final String entityName, final String id) {
    this(String.format("%s with id %s not found", entityName, id));
    this.domain = entityName;
  }

  public EntityNotFoundException(final String message) {
    super(message);
  }
}
