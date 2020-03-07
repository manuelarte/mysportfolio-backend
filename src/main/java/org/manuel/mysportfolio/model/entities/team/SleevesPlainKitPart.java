package org.manuel.mysportfolio.model.entities.team;

import javax.validation.constraints.NotNull;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class SleevesPlainKitPart implements KitPart {

  @NotNull
  private int baseColour;

  @NotNull
  private int sleevesColour;

}
