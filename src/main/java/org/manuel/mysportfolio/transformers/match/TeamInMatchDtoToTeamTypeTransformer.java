package org.manuel.mysportfolio.transformers.match;

import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.team.AnonymousTeamDto;
import org.manuel.mysportfolio.model.dtos.team.RegisteredTeamDto;
import org.manuel.mysportfolio.model.dtos.team.TeamTypeDto;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class TeamInMatchDtoToTeamTypeTransformer<T> implements Function<TeamTypeDto, TeamType> {

  private final RegisteredTeamDtoToRegisteredTeamTransformer registeredTeamDtoToRegisteredTeamTransformer;
  private final AnonymousTeamDtoToAnonymousTeamTransformer anonymousTeamDtoToAnonymousTeamTransformer;

  @Override
  public TeamType apply(final TeamTypeDto o) {
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
