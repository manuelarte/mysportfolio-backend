package org.manuel.mysportfolio.transformers.match;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.match.MatchDto;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.events.MatchEvent;
import org.manuel.mysportfolio.transformers.match.events.MatchEventDtoToMatchEventTransformer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@lombok.AllArgsConstructor
public class MatchDtoToMatchTransformer implements Function<MatchDto, Match> {

    private final TeamInMatchDtoToTeamTypeTransformer teamInMatchDtoToTeamTypeTransformer;
    private final MatchEventDtoToMatchEventTransformer matchEventDtoToMatchEventTransformer;

    @Override
    public Match apply(final MatchDto matchDto) {
        final var match = new Match();
        match.setId(Optional.ofNullable(matchDto.getId()).map(ObjectId::new).orElse(null));
        match.setCompetitionId(Optional.ofNullable(matchDto.getCompetitionId()).map(ObjectId::new).orElse(null));
        match.setVersion(matchDto.getVersion());
        match.setSport(matchDto.getSport());
        match.setType(matchDto.getType());

        match.setAddress(matchDto.getAddress());
        match.setStartDate(matchDto.getStartDate());
        match.setEndDate(matchDto.getEndDate());

        final Stream<MatchEvent> stream = Optional.ofNullable(matchDto.getEvents()).orElse(new ArrayList<>())
                .stream().map(matchEventDtoToMatchEventTransformer);
        match.setEvents(stream.collect(Collectors.toList()));

        match.setPlayedFor(matchDto.getPlayedFor());

        match.setHomeTeam(teamInMatchDtoToTeamTypeTransformer.apply(matchDto.getHomeTeam()));
        match.setAwayTeam(teamInMatchDtoToTeamTypeTransformer.apply(matchDto.getAwayTeam()));

        match.setDescription(matchDto.getDescription());
        match.setChips(match.getChips());

        return match;
    }
}
