package org.manuel.mysportfolio.transformers.match;

import org.manuel.mysportfolio.model.dtos.match.MatchDto;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.transformers.match.events.MatchEventToMatchEventDtoTransformer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@lombok.AllArgsConstructor
public class MatchToMatchDtoTransformer implements Function<Match, MatchDto> {

    private final TeamTypeToTeamInMatchDtoTransformer teamTypeToTeamInMatchDtoTransformer;
    private final MatchEventToMatchEventDtoTransformer matchEventToMatchEventDtoTransformer;

    @Override
    public MatchDto apply(final Match match) {
        final Stream<MatchDto> eventsStream = match.getEvents().stream().map(matchEventToMatchEventDtoTransformer);
        return match == null ? null : MatchDto.builder()
                .id(match.getId().toString())
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
                .createdBy(match.getCreatedBy())
                .build();
    }

}
