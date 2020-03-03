package org.manuel.mysportfolio.transformers.match;

import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.manuel.mysportfolio.model.entities.match.RegisteredTeam;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class TeamTypeToTeamInMatchListDtoTransformer<T> implements Function<TeamType, TeamDto> {

  private final RegisteredTeamToTeamInMatchInListDtoTransformer registeredTeamToTeamInMatchInListDtoTransformer;
  private final AnonymousTeamToTeamInMatchInListDtoTransformer anonymousTeamToTeamInMatchInListDtoTransformer;

  @Override
  public TeamDto apply(final TeamType o) {
    if (o == null) {
      return null;
    }
    if (o instanceof RegisteredTeam) {
      return registeredTeamToTeamInMatchInListDtoTransformer.apply((RegisteredTeam) o);
    }
    if (o instanceof AnonymousTeam) {
      return anonymousTeamToTeamInMatchInListDtoTransformer.apply((AnonymousTeam) o);
    }
    return null;
  }
}
