package org.manuel.mysportfolio.model.entities.team;

import javax.validation.constraints.NotNull;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class PlainKitPart implements KitPart {

  @NotNull
  private int colour;

}
