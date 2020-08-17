package org.manuel.mysportfolio.model.entities.team;

import javax.validation.constraints.NotNull;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Data
public class TeamKit<S extends KitPart, P extends KitPart> {

  @NotNull
  private S shirt;

  @NotNull
  private P pants;

}
