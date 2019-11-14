package org.manuel.mysportfolio.transformers.match;

import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.match.RegisteredTeam;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class RegisteredTeamToTeamInMatchInListDtoTransformer implements Function<RegisteredTeam, TeamDto> {

    private final TeamRepository teamRepository;

    @Override
    public TeamDto apply(final RegisteredTeam registeredTeam) {
        final var team = teamRepository.findById(registeredTeam.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("The team with id %s not found", registeredTeam.getTeamId())));
        return TeamDto.builder()
                        .id(team.getId().toString())
                        .name(team.getName())
                        .teamImage(team.getTeamImage())
                        .build();
    }

}
