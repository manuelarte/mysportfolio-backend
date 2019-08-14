package org.manuel.mysportfolio.transformers;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.team.RegisteredTeamDto;
import org.manuel.mysportfolio.model.entities.match.RegisteredTeam;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class RegisteredTeamDtoToRegisteredTeamTransformer implements Function<RegisteredTeamDto, RegisteredTeam> {

    @Override
    public RegisteredTeam apply(final RegisteredTeamDto registeredTeamDto) {
        return registeredTeamDto == null ? null : createRegisteredTeam(registeredTeamDto);
    }

    private RegisteredTeam createRegisteredTeam(final RegisteredTeamDto registeredTeamDto) {
        final var registeredTeam = new RegisteredTeam();
        registeredTeam.setTeamId(new ObjectId(registeredTeamDto.getTeamId()));
        return registeredTeam;
    }

}
