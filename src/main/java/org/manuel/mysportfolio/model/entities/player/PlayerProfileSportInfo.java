package org.manuel.mysportfolio.model.entities.player;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Data
@lombok.Builder(toBuilder = true)
public class PlayerProfileSportInfo {

  private PlayerProfileFootballInfo footballInfo;
  private PlayerProfileFutsalInfo futsalInfo;

}
