package org.manuel.mysportfolio.model.entities.player;

import java.util.Set;
import javax.validation.constraints.Size;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Data
@lombok.Builder(toBuilder = true)
public class PlayerProfileFootballInfo {

  private FootballPosition preferredPosition;
  @Size(max = 3)
  private Set<FootballPosition> alternativePositions;
  private FootballSkills skills;

}
