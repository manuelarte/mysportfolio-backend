package org.manuel.mysportfolio.transformers.match;

import org.manuel.mysportfolio.model.dtos.match.MatchInListDto;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.manuel.mysportfolio.model.entities.match.events.GoalMatchEvent;
import org.manuel.mysportfolio.model.entities.match.events.MatchEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class MatchToMatchInListDtoTransformer implements Function<Match, MatchInListDto> {

    private final TeamTypeToTeamInMatchListDtoTransformer teamTypeToTeamInMatchListDtoTransformer;

    @Override
    public MatchInListDto apply(final Match match) {
        return match == null ? null : MatchInListDto.builder()
                .id(match.getId().toString())
                .sport(match.getSport())
                .type(match.getType())
                .homeTeam(getTeamName(match.getHomeTeam()))
                .awayTeam(getTeamName(match.getAwayTeam()))
                // TODO
                .homeGoals(getGoals(match.getEvents(), GoalMatchEvent.GoalTeam.HOME_TEAM))
                .awayGoals(getGoals(match.getEvents(), GoalMatchEvent.GoalTeam.AWAY_TEAM))
                .build();
    }

    private <T extends TeamType> String getTeamName(T team) {
        return Optional.ofNullable(teamTypeToTeamInMatchListDtoTransformer.apply(team)).map(TeamDto::getName).orElse(null);
    }

    private int getGoals(final List<MatchEvent> events, final GoalMatchEvent.GoalTeam goalTeam) {
        return (int) events.stream().filter(e -> e instanceof GoalMatchEvent).filter(gE -> goalTeam.equals(((GoalMatchEvent) gE).getTeam())).count();
    }

}
