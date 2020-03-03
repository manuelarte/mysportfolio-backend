package org.manuel.mysportfolio.transformers.team;

import java.util.Optional;
import java.util.function.Function;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamDtoToTeamTransformer implements Function<TeamDto, Team> {

  @Override
  public Team apply(final TeamDto teamDto) {
    final var team = new Team();
    Optional.ofNullable(teamDto.getId()).ifPresent(id -> team.setId(new ObjectId(id)));
    Optional.ofNullable(teamDto.getVersion()).ifPresent(team::setVersion);
    team.setName(teamDto.getName());
    team.setTeamKit(teamDto.getTeamKit());
    team.setTeamImage(teamDto.getTeamImage());

    return team;
  }
}
