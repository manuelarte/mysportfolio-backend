package org.manuel.mysportfolio.model.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@JsonDeserialize(builder = MatchInListDto.MatchInListDtoBuilder.class)
@AllArgsConstructor
@lombok.Value
@Builder(toBuilder = true)
public class MatchInListDto {

    @NotNull
    private UUID id;

    private final String homeTeam;
    private final String awayTeam;

    private final int homeGoals;
    private final int awayGoals;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class MatchInListDtoBuilder {

    }
}
