package org.manuel.mysportfolio.transformers.team;

import io.github.manuelarte.mysportfolio.exceptions.EntityNotFoundException;
import io.github.manuelarte.mysportfolio.model.documents.team.Team;
import io.github.manuelarte.mysportfolio.model.dtos.team.TeamDto;
import java.util.Objects;
import java.util.function.BiFunction;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class TeamDtoToExistingTeamTransformer implements BiFunction<ObjectId, TeamDto, Team> {

  private final TeamQueryService teamQueryService;
  private final TeamImageDtoToTeamImageTransformer teamImageDtoToTeamImageTransformer;
  private final TeamKitDtoToTeamKitTransformer teamKitDtoToTeamKitTransformer;

  @Override
  public Team apply(final ObjectId teamId, final TeamDto teamDto) {
    final var originalTeam = teamQueryService.findOne(teamId)
        .orElseThrow(
            () -> new EntityNotFoundException(Team.class, teamId.toString()));

    final var team = new Team();
    team.setId(originalTeam.getId());
    team.setName(teamDto.getName());
    team.setTeamKit(teamKitDtoToTeamKitTransformer.apply(teamDto.getTeamKit()));
    team.setTeamImage(teamImageDtoToTeamImageTransformer.apply(teamDto.getTeamImage()));
    team.setCreatedBy(Objects.requireNonNull(originalTeam.getCreatedBy().orElse(null)));
    team.setCreatedDate(Objects.requireNonNull(originalTeam.getCreatedDate().orElse(null)));

    return team;
  }
}
