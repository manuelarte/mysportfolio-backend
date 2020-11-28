package org.manuel.mysportfolio.model.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = AppUserDto.AppUserDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class AppUserDto {

  private final Object id;

  private final Long version;

  private final String fullName;

  private final String email;

  private final String picture;

  private final String externalId;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class AppUserDtoBuilder {

  }

}
