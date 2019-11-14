package org.manuel.mysportfolio.model.entities.team;

import javax.validation.constraints.NotNull;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class KitBasicImage implements TeamImage {

    @NotNull
    private int shirtColour;

    @NotNull
    private int pantsColour;

}
