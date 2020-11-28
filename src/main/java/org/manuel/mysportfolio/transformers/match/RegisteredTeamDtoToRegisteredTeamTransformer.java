package org.manuel.mysportfolio.transformers.match;

import io.github.manuelarte.mysportfolio.model.documents.match.RegisteredTeam;
import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.team.RegisteredTeamDto;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class RegisteredTeamDtoToRegisteredTeamTransformer implements
    Function<RegisteredTeamDto, RegisteredTeam> {

  @Override
  public RegisteredTeam apply(final RegisteredTeamDto registeredTeamDto) {
    return registeredTeamDto == null ? null : createRegisteredTeam(registeredTeamDto);
  }

  private RegisteredTeam createRegisteredTeam(final RegisteredTeamDto registeredTeamDto) {
    final var registeredTeam = new RegisteredTeam();
    registeredTeam.setTeamId(registeredTeamDto.getTeamId());
    return registeredTeam;
  }

}
