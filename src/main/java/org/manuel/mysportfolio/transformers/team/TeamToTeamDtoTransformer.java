package org.manuel.mysportfolio.transformers.team;

import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamToTeamDtoTransformer implements Function<Team, TeamDto> {

    @Override
    public TeamDto apply(final Team team) {
        return TeamDto.builder()
                .id(team.getId().toString())
                .version(team.getVersion())
                .name(team.getName())
                .teamKit(team.getTeamKit())
                .teamImage(team.getTeamImage())
                .createdBy(team.getCreatedBy().orElse(null))
                .build();
    }
}
