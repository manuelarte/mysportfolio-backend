package org.manuel.mysportfolio.transformers.team;

import io.github.manuelarte.mysportfolio.model.documents.team.Team;
import io.github.manuelarte.mysportfolio.model.dtos.team.TeamDto;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class TeamDtoToTeamTransformer implements Function<TeamDto, Team> {

  private final TeamImageDtoToTeamImageTransformer teamImageDtoToTeamImageTransformer;
  private final TeamKitDtoToTeamKitTransformer teamKitDtoToTeamKitTransformer;

  @Override
  public Team apply(final TeamDto teamDto) {
    final var team = new Team();
    team.setId(teamDto.getId());
    Optional.ofNullable(teamDto.getVersion()).ifPresent(team::setVersion);
    team.setName(teamDto.getName());
    team.setTeamKit(teamKitDtoToTeamKitTransformer.apply(teamDto.getTeamKit()));
    team.setTeamImage(teamImageDtoToTeamImageTransformer.apply(teamDto.getTeamImage()));

    return team;
  }
}
