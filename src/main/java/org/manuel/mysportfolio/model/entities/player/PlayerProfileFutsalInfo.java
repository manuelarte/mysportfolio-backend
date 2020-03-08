package org.manuel.mysportfolio.model.entities.player;

import java.util.Set;
import javax.validation.constraints.Size;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Data
@lombok.Builder(toBuilder = true)
public class PlayerProfileFutsalInfo {

  private FutsalPosition preferredPosition;
  @Size(max = 3)
  private Set<FutsalPosition> alternativePositions;
  private FootballSkills skills;

}
