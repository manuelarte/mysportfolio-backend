package org.manuel.mysportfolio.transformers.team;

import io.github.manuelarte.mysportfolio.model.documents.team.Team;
import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.springframework.stereotype.Component;

@Component
@lombok.RequiredArgsConstructor
public class TeamToTeamDtoTransformer implements Function<Team, TeamDto> {

  private final TeamImageToTeamImageDtoTransformer teamImageToTeamImageDtoTransformer;
  private final TeamKitToTeamKitDtoTransformer teamKitToTeamKitDtoTransformer;

  @Override
  public TeamDto apply(final Team team) {
    return TeamDto.builder()
        .id(team.getId())
        .version(team.getVersion())
        .name(team.getName())
        .teamKit(teamKitToTeamKitDtoTransformer.apply(team.getTeamKit()))
        .teamImage(teamImageToTeamImageDtoTransformer.apply(team.getTeamImage()))
        .createdBy(team.getCreatedBy().orElse(null))
        .build();
  }
}
