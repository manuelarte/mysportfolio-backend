package org.manuel.mysportfolio.transformers.match;

import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.team.AnonymousTeamDto;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class AnonymousTeamToAnonymousTeamDtoTransformer implements
    Function<AnonymousTeam, AnonymousTeamDto> {

  @Override
  public AnonymousTeamDto apply(final AnonymousTeam anonymousTeam) {
    return anonymousTeam == null ? null : AnonymousTeamDto.builder()
        .name(anonymousTeam.getName())
        .teamKit(anonymousTeam.getTeamKit())
        .teamImage(anonymousTeam.getTeamImage())
        .build();
  }

}
