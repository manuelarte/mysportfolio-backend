package org.manuel.mysportfolio.model.dtos.team.kits;

import javax.validation.constraints.NotNull;
import org.manuel.mysportfolio.model.dtos.team.KitPartDto;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class VShapeKitPartDto implements KitPartDto {

  @NotNull
  private int baseColour;

  @NotNull
  private int vShapeColour;

}