package org.manuel.mysportfolio.model.dtos.team.kits;

import io.github.manuelarte.mysportfolio.model.documents.team.kits.StripeKitPart;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.manuel.mysportfolio.model.dtos.team.KitPartDto;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class StripeKitPartDto implements KitPartDto {

  @NotNull
  private int baseColour;
  @NotNull
  @Min(3)
  @Max(7)
  private int numberOfStripes;
  @NotNull
  private int stripeColour;
  @NotNull
  private StripeKitPart.StripeOrientation orientation;

}
