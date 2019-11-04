package org.manuel.mysportfolio.transformers.match;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.match.MatchDto;
import org.manuel.mysportfolio.model.dtos.match.MatchUpdateDto;
import org.manuel.mysportfolio.model.dtos.team.TeamTypeDto;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.manuel.mysportfolio.model.entities.match.events.MatchEvent;
import org.manuel.mysportfolio.transformers.match.events.MatchEventDtoToMatchEventTransformer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@lombok.AllArgsConstructor
public class MatchUpdateDtoToMatchTransformer implements
        BiFunction<Match<? extends TeamType, ? extends TeamType>, MatchUpdateDto<? extends TeamTypeDto, ? extends TeamTypeDto>, Match<TeamType, TeamType>> {

    private final TeamInMatchDtoToTeamTypeTransformer teamInMatchDtoToTeamTypeTransformer;
    private final MatchEventDtoToMatchEventTransformer matchEventDtoToMatchEventTransformer;

    @Override
    public Match<TeamType, TeamType> apply(final Match<? extends TeamType, ? extends TeamType> originalMatch, final MatchUpdateDto<? extends TeamTypeDto, ? extends TeamTypeDto> matchUpdateDto) {
        final var updatedMatch = new Match<>();

        // Fields that should never change
        updatedMatch.setId(originalMatch.getId());
        updatedMatch.setCreatedBy(originalMatch.getCreatedBy().get());
        updatedMatch.setCreatedDate(originalMatch.getCreatedDate().get());
        updatedMatch.setLastModifiedBy(originalMatch.getLastModifiedBy().get());
        updatedMatch.setLastModifiedDate(originalMatch.getLastModifiedDate().get());

        // Fields that can be modified
        updatedMatch.setVersion(matchUpdateDto.getVersion());
        updatedMatch.setCompetitionId(Optional.ofNullable(matchUpdateDto.getCompetitionId()).map(ObjectId::new).orElse(null));
        updatedMatch.setSport(matchUpdateDto.getSport());
        updatedMatch.setType(matchUpdateDto.getType());

        updatedMatch.setAddress(matchUpdateDto.getAddress());
        updatedMatch.setStartDate(matchUpdateDto.getStartDate());
        updatedMatch.setEndDate(matchUpdateDto.getEndDate());

        final Stream<MatchEvent> stream = Optional.ofNullable(matchUpdateDto.getEvents()).orElse(new ArrayList<>())
                .stream().map(matchEventDtoToMatchEventTransformer);
        updatedMatch.setEvents(stream.collect(Collectors.toList()));

        updatedMatch.setPlayedFor(matchUpdateDto.getPlayedFor());

        updatedMatch.setHomeTeam(teamInMatchDtoToTeamTypeTransformer.apply(matchUpdateDto.getHomeTeam()));
        updatedMatch.setAwayTeam(teamInMatchDtoToTeamTypeTransformer.apply(matchUpdateDto.getAwayTeam()));

        updatedMatch.setDescription(matchUpdateDto.getDescription());
        updatedMatch.setChips(matchUpdateDto.getChips());

        return updatedMatch;
    }
}
