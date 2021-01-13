package org.manuel.mysportfolio.transformers.match;

import io.github.manuelarte.mysportfolio.model.documents.match.AnonymousTeam;
import io.github.manuelarte.mysportfolio.model.documents.match.RegisteredTeam;
import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import io.github.manuelarte.mysportfolio.model.dtos.team.TeamTypeDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class TeamTypeToTeamInMatchDtoTransformer implements Function<TeamType, TeamTypeDto> {

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
