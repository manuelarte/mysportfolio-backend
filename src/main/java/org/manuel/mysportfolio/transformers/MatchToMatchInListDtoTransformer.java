package org.manuel.mysportfolio.transformers;

import org.manuel.mysportfolio.model.dtos.match.MatchInListDto;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class MatchToMatchInListDtoTransformer implements Function<Match, MatchInListDto> {

    private final TeamTypeToTeamDtoTransformer teamTypeToTeamDtoTransformer;

    @Override
    public MatchInListDto apply(final Match match) {
        return match == null ? null : MatchInListDto.builder()
                .id(match.getId().toString())
                .homeTeam(getTeamName(match.getHomeTeam()))
                .awayTeam(getTeamName(match.getAwayTeam()))
                // TODO
                .homeGoals(2)
                .awayGoals(1)
                .build();
    }

    private <T extends TeamType> String getTeamName(T team) {
        return Optional.ofNullable(teamTypeToTeamDtoTransformer.apply(team)).map(TeamDto::getName).orElse(null);
    }

}
