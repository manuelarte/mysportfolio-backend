package org.manuel.mysportfolio.model.dtos.match;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.manuel.mysportfolio.model.dtos.team.TeamInMatchDto;

@JsonDeserialize(builder = PerformanceDto.PerformanceDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class PerformanceDto {

    @Digits(integer=2, fraction=2)
    @Size(min = 0, max = 10)
    @NotNull
    private final BigDecimal performance;

    @Size(max = 500)
    private final String description;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class PerformanceDtoBuilder {

    }

}
