package org.manuel.mysportfolio.model.dtos.match;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.apache.logging.log4j.util.Strings;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.manuel.mysportfolio.model.entities.TeamOption;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Dto to be used when retrieving a list of matches
 */
@JsonDeserialize(builder = MatchInListDto.MatchInListDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class MatchInListDto {

    @NotNull
    private final String id;

    private final String competitionId;

    @NotNull
    private final Long version;

    @NotNull
    private final Sport sport;

    private final SportType type;

    @NotNull
    @Past
    private final Instant startDate;

    @NotNull
    private final String homeTeam;

    @NotNull
    private final String awayTeam;

    @NotNull
    private final Map<String, TeamOption> playedFor;

    @Min(0)
    private final int homeGoals;
    @Min(0)
    private final int awayGoals;

    private final Set<String> chips;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class MatchInListDtoBuilder {

    }

    @AssertTrue
    @JsonIgnore
    private boolean isOneTeamExist() {
        return Strings.isNotEmpty(homeTeam) || Strings.isNotEmpty(awayTeam);
    }
}
