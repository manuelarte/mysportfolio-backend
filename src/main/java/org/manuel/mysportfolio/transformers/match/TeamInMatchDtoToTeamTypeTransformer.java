package org.manuel.mysportfolio.transformers.match;

import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.team.AnonymousTeamDto;
import org.manuel.mysportfolio.model.dtos.team.RegisteredTeamDto;
import org.manuel.mysportfolio.model.dtos.team.TeamTypeDto;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class TeamInMatchDtoToTeamTypeTransformer implements Function<TeamTypeDto, TeamType> {

  private final RegisteredTeamDtoToRegisteredTeamTransformer registeredTeamDtoToRegisteredTeamTransformer;
  private final AnonymousTeamDtoToAnonymousTeamTransformer anonymousTeamDtoToAnonymousTeamTransformer;

  @Override
  public TeamType apply(final TeamTypeDto o) {
    if (o instanceof RegisteredTeamDto) {
      return registeredTeamDtoToRegisteredTeamTransformer.apply((RegisteredTeamDto) o);
    }
    if (o instanceof AnonymousTeamDto) {
      return anonymousTeamDtoToAnonymousTeamTransformer.apply((AnonymousTeamDto) o);
    }
    return null;
  }
}
