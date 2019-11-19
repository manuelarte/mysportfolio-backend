package org.manuel.mysportfolio.model.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.manuel.mysportfolio.model.dtos.match.MatchDto;
import org.manuel.mysportfolio.model.dtos.match.PerformanceDto;

import javax.validation.constraints.NotNull;

@JsonDeserialize(builder = UserMatchDto.UserMatchDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class UserMatchDto {

    @NotNull
    private final MatchDto match;

    private final PerformanceDto performance;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class UserMatchDtoBuilder {

    }

}
