package org.manuel.mysportfolio.model.entities.team;

import javax.validation.constraints.NotNull;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Data
public class TeamKit<Shirt extends KitPart, Pants extends KitPart> {

  @NotNull
  private Shirt shirt;

  @NotNull
  private Pants pants;

}
