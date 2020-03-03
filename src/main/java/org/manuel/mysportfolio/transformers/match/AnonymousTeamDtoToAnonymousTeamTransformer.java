package org.manuel.mysportfolio.transformers.match;

import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.team.AnonymousTeamDto;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class AnonymousTeamDtoToAnonymousTeamTransformer implements
    Function<AnonymousTeamDto, AnonymousTeam> {

  @Override
  public AnonymousTeam apply(final AnonymousTeamDto anonymousTeamDto) {
    return anonymousTeamDto == null ? null : create(anonymousTeamDto);
  }

  private AnonymousTeam create(final AnonymousTeamDto anonymousTeamDto) {
    final var anonymousTeam = new AnonymousTeam();
    anonymousTeam.setName(anonymousTeamDto.getName());
    anonymousTeam.setTeamKit(anonymousTeamDto.getTeamKit());
    anonymousTeam.setTeamImage(anonymousTeamDto.getTeamImage());
    return anonymousTeam;
  }

}
