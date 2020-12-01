package org.manuel.mysportfolio.model.dtos.playerprofile;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Data
@lombok.extern.jackson.Jacksonized @lombok.Builder(toBuilder = true)
public class PlayerProfileSportInfoDto {

  private final PlayerProfileFootballInfoDto footballInfo;
  private final PlayerProfileFutsalInfoDto futsalInfo;

}
