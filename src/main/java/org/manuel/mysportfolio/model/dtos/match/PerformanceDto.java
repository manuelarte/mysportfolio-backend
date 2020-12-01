package org.manuel.mysportfolio.model.dtos.match;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Immutable
@lombok.AllArgsConstructor
@lombok.Value
@lombok.extern.jackson.Jacksonized @lombok.Builder(toBuilder = true)
public class PerformanceDto {

  @Digits(integer = 2, fraction = 2)
  @Size(max = 10)
  @NotNull
  private final BigDecimal rate;

  @Size(max = 200)
  private final String notes;

}
