package org.manuel.mysportfolio.transformers.match;

import org.manuel.mysportfolio.model.dtos.match.MatchDto;
import org.manuel.mysportfolio.model.dtos.match.MatchEventDto;
import org.manuel.mysportfolio.model.dtos.team.TeamInMatchDto;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.manuel.mysportfolio.transformers.match.events.MatchEventToMatchEventDtoTransformer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@lombok.AllArgsConstructor
public class MatchToMatchDtoTransformer implements Function<Match<? extends TeamType, ? extends TeamType>, MatchDto<TeamInMatchDto, TeamInMatchDto>> {

    private final TeamTypeToTeamInMatchDtoTransformer teamTypeToTeamInMatchDtoTransformer;
    private final MatchEventToMatchEventDtoTransformer matchEventToMatchEventDtoTransformer;

    @Override
    public MatchDto<TeamInMatchDto, TeamInMatchDto> apply(final Match<?, ?> match) {
        final Stream<MatchEventDto> eventsStream = match.getEvents().stream().map(matchEventToMatchEventDtoTransformer);
        return match == null ? null : MatchDto.builder()
                .id(match.getId().toString())
                .competitionId(Optional.ofNullable(match.getCompetitionId()).map(c -> c.toString()).orElse(null))
                .version(match.getVersion())
                .sport(match.getSport())
                .type(match.getType())
                .playedFor(match.getPlayedFor())
                .homeTeam(teamTypeToTeamInMatchDtoTransformer.apply(match.getHomeTeam()))
                .awayTeam(teamTypeToTeamInMatchDtoTransformer.apply(match.getAwayTeam()))
                .address(match.getAddress())
                .startDate(match.getStartDate())
                .endDate(match.getEndDate())
                .events(eventsStream.collect(Collectors.toList()))
                .description(match.getDescription())
                .chips(match.getChips())
                .createdBy(match.getCreatedBy().orElse(null))
                .build();
    }

}
