package org.manuel.mysportfolio.model.entities.match;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Performance {

    @Digits(integer = 10, fraction = 2)
    @Max(10)
    @NotNull
    private BigDecimal rate;

    @Size(max = 200)
    private String notes;

}
