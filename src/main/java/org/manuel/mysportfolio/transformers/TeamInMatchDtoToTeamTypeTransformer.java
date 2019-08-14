package org.manuel.mysportfolio.transformers;

import org.manuel.mysportfolio.model.dtos.team.AnonymousTeamDto;
import org.manuel.mysportfolio.model.dtos.team.RegisteredTeamDto;
import org.manuel.mysportfolio.model.dtos.team.TeamInMatchDto;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class TeamInMatchDtoToTeamTypeTransformer<T> implements Function<TeamInMatchDto, TeamType> {

    private final RegisteredTeamDtoToRegisteredTeamTransformer registeredTeamDtoToRegisteredTeamTransformer;
    private final AnonymousTeamDtoToAnonymousTeamTransformer anonymousTeamDtoToAnonymousTeamTransformer;

    @Override
    public TeamType apply(final TeamInMatchDto o) {
        if (o == null) {
            return null;
        }
        if (o instanceof RegisteredTeamDto) {
            return registeredTeamDtoToRegisteredTeamTransformer.apply((RegisteredTeamDto) o);
        }
        if (o instanceof AnonymousTeamDto) {
            return anonymousTeamDtoToAnonymousTeamTransformer.apply((AnonymousTeamDto) o);
        }
        return null;
    }
}
