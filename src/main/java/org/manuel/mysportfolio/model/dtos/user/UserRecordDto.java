package org.manuel.mysportfolio.model.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

/**
 * User information coming from Firebase database
 */
@JsonDeserialize(builder = UserRecordDto.UserRecordDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class UserRecordDto {

  private final String uid;

  @NotNull
  private final String displayName;

  @URL
  private final String photoUrl;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class UserRecordDtoBuilder {

  }

}
