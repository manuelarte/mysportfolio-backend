package org.manuel.mysportfolio.transformers.team;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.BiFunction;

@Component
@lombok.AllArgsConstructor
public class PartialTeamDtoToTeamTransformer implements BiFunction<String, TeamDto, Team> {

    private final TeamQueryService teamQueryService;

    @Override
    public Team apply(final String teamId, final TeamDto updatedFieldsTeamDto) {
        final var originalTeam = teamQueryService.findOne(new ObjectId(teamId))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Team with id %s not found and can't be patch", updatedFieldsTeamDto.getId())));
        final var mixed = originalTeam.toBuilder();
        Optional.ofNullable(updatedFieldsTeamDto.getName()).ifPresent(name -> mixed.name(name));
        Optional.ofNullable(updatedFieldsTeamDto.getImageLink()).ifPresent(imageLink -> mixed.imageLink(imageLink));
        return mixed.build();
    }
}
