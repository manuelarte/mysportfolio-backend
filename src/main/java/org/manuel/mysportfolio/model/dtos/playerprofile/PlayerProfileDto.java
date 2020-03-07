package org.manuel.mysportfolio.model.dtos.playerprofile;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.Year;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

@JsonDeserialize(builder = PlayerProfileDto.PlayerProfileDtoBuilder.class)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class PlayerProfileDto {

  @NotNull
  private final String id;

  private final Map<
      @PastOrPresent
          Year,
      @Valid
          PlayerProfileSportInfoDto> info;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class PlayerProfileDtoBuilder {

  }

}
