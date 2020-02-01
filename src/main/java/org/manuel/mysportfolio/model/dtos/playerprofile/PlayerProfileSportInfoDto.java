package org.manuel.mysportfolio.model.dtos.playerprofile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = PlayerProfileSportInfoDto.PlayerProfileSportInfoDtoBuilder.class)
@lombok.AllArgsConstructor
@lombok.Data
@lombok.Builder(toBuilder = true)
public class PlayerProfileSportInfoDto {

  private final PlayerProfileFootballInfoDto footballInfo;
  private final PlayerProfileFutsalInfoDto futsalInfo;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class PlayerProfileSportInfoDtoBuilder {

  }

}
