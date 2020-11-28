package org.manuel.mysportfolio.transformers.match;

import io.github.manuelarte.mysportfolio.model.documents.match.RegisteredTeam;
import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.team.RegisteredTeamDto;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class RegisteredTeamToRegisteredTeamDtoTransformer implements
    Function<RegisteredTeam, RegisteredTeamDto> {

  @Override
  public RegisteredTeamDto apply(final RegisteredTeam registeredTeam) {
    return registeredTeam == null ? null : RegisteredTeamDto.builder()
        .teamId(registeredTeam.getTeamId())
        .build();
  }

}
