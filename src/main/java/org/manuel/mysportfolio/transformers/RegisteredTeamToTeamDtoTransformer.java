package org.manuel.mysportfolio.transformers;

import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.match.RegisteredTeam;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class RegisteredTeamToTeamDtoTransformer implements Function<RegisteredTeam, TeamDto> {

    private final TeamRepository teamRepository;

    @Override
    public TeamDto apply(final RegisteredTeam registeredTeam) {
        final var team = teamRepository.findById(registeredTeam.getTeamId())
                .orElseThrow();
        return registeredTeam == null ? null : TeamDto.builder()
                .name(team.getName())
                .imageLink(team.getImageLink())
                .build();
    }

}
