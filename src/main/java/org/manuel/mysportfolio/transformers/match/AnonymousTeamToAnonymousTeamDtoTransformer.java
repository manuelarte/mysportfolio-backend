package org.manuel.mysportfolio.transformers.match;

import io.github.manuelarte.mysportfolio.model.documents.match.AnonymousTeam;
import io.github.manuelarte.mysportfolio.model.dtos.team.AnonymousTeamDto;
import java.util.function.Function;
import org.manuel.mysportfolio.transformers.team.TeamImageToTeamImageDtoTransformer;
import org.manuel.mysportfolio.transformers.team.TeamKitToTeamKitDtoTransformer;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class AnonymousTeamToAnonymousTeamDtoTransformer implements
    Function<AnonymousTeam, AnonymousTeamDto> {

  private final TeamImageToTeamImageDtoTransformer teamImageToTeamImageDtoTransformer;
  private final TeamKitToTeamKitDtoTransformer teamKitToTeamKitDtoTransformer;

  @Override
  public AnonymousTeamDto apply(final AnonymousTeam anonymousTeam) {
    return anonymousTeam == null ? null : AnonymousTeamDto.builder()
        .name(anonymousTeam.getName())
        .teamKit(teamKitToTeamKitDtoTransformer.apply(anonymousTeam.getTeamKit()))
        .teamImage(teamImageToTeamImageDtoTransformer.apply(anonymousTeam.getTeamImage()))
        .build();
  }

}
