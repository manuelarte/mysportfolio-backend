package org.manuel.mysportfolio.model.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.NotNull;
import org.manuel.mysportfolio.model.Badge;

@JsonDeserialize(builder = AppBadgeDto.AppBadgeDtoBuilder.class)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class AppBadgeDto {

  @NotNull
  private final Badge badge;

  @NotNull
  private final String displayName;

  @NotNull
  @org.hibernate.validator.constraints.URL
  private final String imageUrl;

  @NotNull
  private final int points;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class AppBadgeDtoBuilder {

  }

}
