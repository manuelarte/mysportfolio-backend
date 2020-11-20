package org.manuel.mysportfolio.transformers.team;

import io.github.manuelarte.mysportfolio.model.documents.team.Team;
import java.util.Optional;
import java.util.function.Function;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class TeamDtoToTeamTransformer implements Function<TeamDto, Team> {

  private final TeamImageDtoToTeamImageTransformer teamImageDtoToTeamImageTransformer;
  private final TeamKitDtoToTeamKitTransformer teamKitDtoToTeamKitTransformer;

  @Override
  public Team apply(final TeamDto teamDto) {
    final var team = new Team();
    Optional.ofNullable(teamDto.getId()).ifPresent(id -> team.setId(new ObjectId(id)));
    Optional.ofNullable(teamDto.getVersion()).ifPresent(team::setVersion);
    team.setName(teamDto.getName());
    team.setTeamKit(teamKitDtoToTeamKitTransformer.apply(teamDto.getTeamKit()));
    team.setTeamImage(teamImageDtoToTeamImageTransformer.apply(teamDto.getTeamImage()));

    return team;
  }
}
