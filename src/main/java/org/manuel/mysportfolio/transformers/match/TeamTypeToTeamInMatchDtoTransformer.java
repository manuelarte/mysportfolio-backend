package org.manuel.mysportfolio.transformers.match;

import org.manuel.mysportfolio.model.dtos.team.TeamTypeDto;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.manuel.mysportfolio.model.entities.match.RegisteredTeam;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class TeamTypeToTeamInMatchDtoTransformer<T> implements Function<TeamType, TeamTypeDto> {

    private final RegisteredTeamToRegisteredTeamDtoTransformer registeredTeamToRegisteredTeamDtoTransformer;
    private final AnonymousTeamToAnonymousTeamDtoTransformer anonymousTeamToAnonymousTeamDtoTransformer;

    @Override
    public TeamTypeDto apply(final TeamType o) {
        if (o == null) {
            return null;
        }
        if (o instanceof RegisteredTeam) {
            return registeredTeamToRegisteredTeamDtoTransformer.apply((RegisteredTeam) o);
        }
        if (o instanceof AnonymousTeam) {
            return anonymousTeamToAnonymousTeamDtoTransformer.apply((AnonymousTeam) o);
        }
        return null;
    }
}
