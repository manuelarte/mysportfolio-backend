package org.manuel.mysportfolio.transformers;

import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.manuel.mysportfolio.model.entities.match.RegisteredTeam;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class TeamTypeToTeamDtoTransformer<T> implements Function<TeamType, TeamDto> {

    private final RegisteredTeamToTeamDtoTransformer registeredTeamToTeamDtoTransformer;
    private final AnonymousTeamToTeamDtoTransformer anonymousTeamToTeamDtoTransformer;

    @Override
    public TeamDto apply(TeamType o) {
        if (o == null) {
            return null;
        }
        if (o instanceof RegisteredTeam) {
            return registeredTeamToTeamDtoTransformer.apply((RegisteredTeam) o);
        }
        if (o instanceof AnonymousTeam) {
            return anonymousTeamToTeamDtoTransformer.apply((AnonymousTeam) o);
        }
        return null;
    }
}
