package org.manuel.mysportfolio.transformers.match;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.match.MatchDto;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class MatchDtoToMatchTransformer implements Function<MatchDto, Match> {

    private final TeamInMatchDtoToTeamTypeTransformer teamInMatchDtoToTeamTypeTransformer;

    @Override
    public Match apply(MatchDto matchDto) {
        final var match = new Match();
        match.setId(Optional.ofNullable(matchDto.getId()).map(ObjectId::new).orElse(null));
        match.setSport(matchDto.getSport());
        match.setType(matchDto.getType());

        match.setHomeTeam(teamInMatchDtoToTeamTypeTransformer.apply(matchDto.getHomeTeam()));
        match.setAwayTeam(teamInMatchDtoToTeamTypeTransformer.apply(matchDto.getAwayTeam()));

        return match;
    }
}
