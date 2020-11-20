package org.manuel.mysportfolio.transformers.team;

import io.github.manuelarte.mysportfolio.model.documents.team.Team;
import java.util.function.BiFunction;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class TeamDtoToExistingTeamTransformer implements BiFunction<String, TeamDto, Team> {

  private final TeamQueryService teamQueryService;
  private final TeamImageDtoToTeamImageTransformer teamImageDtoToTeamImageTransformer;
  private final TeamKitDtoToTeamKitTransformer teamKitDtoToTeamKitTransformer;

  @Override
  public Team apply(final String teamId, final TeamDto teamDto) {
    final var originalTeam = teamQueryService.findOne(new ObjectId(teamId))
        .orElseThrow(
            () -> new EntityNotFoundException(String.format("Team with id %s not found", teamId)));

    final var team = new Team();
    team.setId(originalTeam.getId());
    team.setName(teamDto.getName());
    team.setTeamKit(teamKitDtoToTeamKitTransformer.apply(teamDto.getTeamKit()));
    team.setTeamImage(teamImageDtoToTeamImageTransformer.apply(teamDto.getTeamImage()));
    team.setCreatedBy(originalTeam.getCreatedBy().orElse(null));
    team.setCreatedDate(originalTeam.getCreatedDate().orElse(null));

    return team;
  }
}
