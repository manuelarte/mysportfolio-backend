package org.manuel.mysportfolio.model.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

/**
 * User information coming from Firebase database.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.extern.jackson.Jacksonized @lombok.Builder(toBuilder = true)
public class UserRecordDto {

  private final String uid;

  @NotNull
  private final String displayName;

  @URL
  private final String photoUrl;

}
