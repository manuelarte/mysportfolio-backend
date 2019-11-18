package org.manuel.mysportfolio.transformers.team;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
@lombok.AllArgsConstructor
public class PartialTeamDtoToTeamTransformer implements BiFunction<String, TeamDto, Team> {

    private final TeamQueryService teamQueryService;

    @Override
    public Team apply(@NotNull final String teamId, @NotNull final TeamDto updatedFieldsTeamDto) {
        Assert.notNull(teamId, "The team id in a partial update can't be null");
        Assert.notNull(updatedFieldsTeamDto, "The updated pojo for team in a partial update can't be null");
        final var originalTeam = teamQueryService.findOne(new ObjectId(teamId))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Team with id %s not found and can't be patch", updatedFieldsTeamDto.getId())));
        final var mixed = originalTeam.toBuilder();
        Optional.ofNullable(updatedFieldsTeamDto.getName()).ifPresent(mixed::name);
        Optional.ofNullable(updatedFieldsTeamDto.getTeamKit()).ifPresent(mixed::teamKit);
        Optional.ofNullable(updatedFieldsTeamDto.getTeamImage()).ifPresent(mixed::teamImage);
        return mixed.build();
    }
}
