package org.manuel.mysportfolio.transformers.match;

import org.manuel.mysportfolio.model.dtos.team.RegisteredTeamDto;
import org.manuel.mysportfolio.model.entities.match.RegisteredTeam;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class RegisteredTeamToRegisteredTeamDtoTransformer implements Function<RegisteredTeam, RegisteredTeamDto> {

    @Override
    public RegisteredTeamDto apply(final RegisteredTeam registeredTeam) {
        return registeredTeam == null ? null : RegisteredTeamDto.builder()
                .teamId(registeredTeam.getTeamId().toString())
                .build();
    }

}
