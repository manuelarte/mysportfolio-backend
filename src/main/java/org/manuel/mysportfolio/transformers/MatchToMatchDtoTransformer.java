package org.manuel.mysportfolio.transformers;

import org.manuel.mysportfolio.model.dtos.match.MatchDto;
import org.manuel.mysportfolio.model.dtos.match.MatchInListDto;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class MatchToMatchDtoTransformer implements Function<Match, MatchDto> {

    private final TeamTypeToTeamInMatchDtoTransformer teamTypeToTeamInMatchDtoTransformer;

    @Override
    public MatchDto apply(final Match match) {
        return match == null ? null : MatchDto.builder()
                .id(match.getId().toString())
                .sport(match.getSport())
                .type(match.getType())
                .homeTeam(teamTypeToTeamInMatchDtoTransformer.apply(match.getHomeTeam()))
                .awayTeam(teamTypeToTeamInMatchDtoTransformer.apply(match.getAwayTeam()))
                .creator(match.getCreator())
                .build();
    }

}
