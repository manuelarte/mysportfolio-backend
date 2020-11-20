package org.manuel.mysportfolio.model.dtos.team;

import javax.validation.constraints.NotNull;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Data
public class TeamKitDto<S extends KitPartDto, P extends KitPartDto> {

  @NotNull
  private S shirt;

  @NotNull
  private P pants;

}
