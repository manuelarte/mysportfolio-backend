package org.manuel.mysportfolio.model.dtos.match;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.math.BigDecimal;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonDeserialize(builder = PerformanceDto.PerformanceDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Immutable
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class PerformanceDto {

  @Digits(integer = 2, fraction = 2)
  @Size(max = 10)
  @NotNull
  private final BigDecimal rate;

  @Size(max = 200)
  private final String notes;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class PerformanceDtoBuilder {

  }

}
