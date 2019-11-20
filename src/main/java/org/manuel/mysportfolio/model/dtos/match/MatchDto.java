package org.manuel.mysportfolio.model.dtos.match;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.manuel.mysportfolio.model.dtos.team.TeamTypeDto;
import org.manuel.mysportfolio.model.entities.Place;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.validation.NewEntity;
import org.manuel.mysportfolio.validation.PartialUpdateEntity;
import org.manuel.mysportfolio.validation.UpdateEntity;

import javax.validation.constraints.*;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@JsonDeserialize(builder = MatchDto.MatchDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class MatchDto<HomeTeam extends TeamTypeDto, AwayTeam extends TeamTypeDto> {

    @Null(groups = {NewEntity.class, PartialUpdateEntity.class})
    @NotNull(groups = UpdateEntity.class)
    private final String id;

    @Null(groups = NewEntity.class)
    @NotNull(groups = { UpdateEntity.class, PartialUpdateEntity.class })
    private final Long version;

    @Size(max = 24)
    private final String competitionId;

    @NotNull
    private final Sport sport;

    private final SportType type;

    @NotNull
    private final Map<String, TeamOption> playedFor;

    private final HomeTeam homeTeam;

    private final AwayTeam awayTeam;

    private final List<MatchEventDto> events;

    private final Place address;

    @PastOrPresent
    @NotNull
    private final Instant startDate;
    private final Instant endDate;

    private final String description;

    @Size(max = 5)
    private final Set<String> chips;

    @Null(groups = {NewEntity.class, UpdateEntity.class, PartialUpdateEntity.class})
    private final String createdBy;

    public static <HomeTeam extends TeamTypeDto, AwayTeam extends TeamTypeDto> MatchDtoBuilder builder() {
        return new MatchDtoBuilder<HomeTeam, AwayTeam>();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class MatchDtoBuilder<HomeTeam extends TeamTypeDto, AwayTeam extends TeamTypeDto> {
    }

    @AssertTrue
    @JsonIgnore
    @SuppressWarnings("unused")
    private boolean isOneTeamExist() {
        return Objects.nonNull(homeTeam) || Objects.nonNull(awayTeam);
    }

}
