package org.manuel.mysportfolio.model.entities.team;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class StripeKitPart implements KitPart {

  @NotNull
  private int baseColour;
  @NotNull
  @Min(3)
  @Max(7)
  private int numberOfStripes;
  @NotNull
  private int stripeColour;
  @NotNull
  private StripeOrientation orientation;

  public enum StripeOrientation {
    VERTICAL, HORIZONTAL
  }

}
