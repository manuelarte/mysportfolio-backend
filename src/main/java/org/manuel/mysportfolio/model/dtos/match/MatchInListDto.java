package org.manuel.mysportfolio.model.dtos.match;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.manuel.mysportfolio.model.entities.TeamOption;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Dto to be used when retrieving a list of matches
 */
@JsonDeserialize(builder = MatchInListDto.MatchInListDtoBuilder.class)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class MatchInListDto {

    @NotNull
    private final String id;

    @NotNull
    private final Sport sport;

    private final SportType type;

    private final String homeTeam;
    private final String awayTeam;

    private final TeamOption playedFor;

    @Min(0)
    private final int homeGoals;
    @Min(0)
    private final int awayGoals;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class MatchInListDtoBuilder {

    }
}
