package org.manuel.mysportfolio.transformers.team;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class TeamDtoToExistingTeamTransformer implements BiFunction<String, TeamDto, Team> {

    private final TeamQueryService teamQueryService;

    @Override
    public Team apply(final String teamId, final TeamDto teamDto) {
        final var originalTeam = teamQueryService.findOne(new ObjectId(teamId))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Team with id %s not found", teamId)));

        final var team = new Team();
        team.setId(originalTeam.getId());
        team.setName(teamDto.getName());
        team.setImageLink(teamDto.getImageLink());
        team.setCreatedBy(originalTeam.getCreatedBy());
        team.setCreatedDate(originalTeam.getCreatedDate());

        return team;
    }
}
