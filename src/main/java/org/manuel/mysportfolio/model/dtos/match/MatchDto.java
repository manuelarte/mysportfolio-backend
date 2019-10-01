package org.manuel.mysportfolio.model.dtos.match;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.manuel.mysportfolio.model.dtos.team.TeamInMatchDto;
import org.manuel.mysportfolio.model.entities.TeamOption;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

@JsonDeserialize(builder = MatchDto.MatchDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class MatchDto<HomeTeam extends TeamInMatchDto, AwayTeam extends TeamInMatchDto> {

    private final String id;

    @NotNull
    private final Sport sport;

    private final SportType type;

    @NotNull
    private final Map<String, TeamOption> playedFor;

    @NotNull
    private final HomeTeam homeTeam;

    @NotNull
    private final AwayTeam awayTeam;

    private final List<MatchEventDto> events;

    private final String address;

    @Past
    @NotNull
    private final Instant startDate;
    private final Instant endDate;

    private final String description;

    private final Set<String> chips;

    private final String createdBy;

    public static <HomeTeam extends TeamInMatchDto, AwayTeam extends TeamInMatchDto> MatchDtoBuilder builder() {
        return new MatchDtoBuilder<HomeTeam, AwayTeam>();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class MatchDtoBuilder<HomeTeam extends TeamInMatchDto, AwayTeam extends TeamInMatchDto> {
    }

}
