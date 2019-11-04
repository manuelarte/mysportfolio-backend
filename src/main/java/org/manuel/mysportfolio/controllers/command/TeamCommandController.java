package org.manuel.mysportfolio.controllers.command;

import io.jsonwebtoken.lang.Assert;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.services.command.TeamCommandService;
import org.manuel.mysportfolio.transformers.team.PartialTeamDtoToTeamTransformer;
import org.manuel.mysportfolio.transformers.team.TeamDtoToExistingTeamTransformer;
import org.manuel.mysportfolio.transformers.team.TeamDtoToTeamTransformer;
import org.manuel.mysportfolio.transformers.team.TeamToTeamDtoTransformer;
import org.manuel.mysportfolio.validation.NewEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.groups.Default;

@RestController
@RequestMapping("/api/v1/teams")
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class TeamCommandController {

    private final TeamCommandService teamCommandService;
    private final TeamDtoToTeamTransformer teamDtoToTeamTransformer;
    private final TeamToTeamDtoTransformer teamToTeamDtoTransformer;
    private final TeamDtoToExistingTeamTransformer teamDtoToExistingTeamTransformer;
    private final PartialTeamDtoToTeamTransformer partialTeamDtoToTeamTransformer;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TeamDto> saveTeam(@Validated({Default.class, NewEntity.class}) @RequestBody final TeamDto teamDto) {
        final var saved = teamCommandService.save(teamDtoToTeamTransformer.apply(teamDto));
        final var location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();
        log.info("Team with id {}, created by {} saved", saved.getId(), saved.getCreatedBy());
        return ResponseEntity.created(location).body(teamToTeamDtoTransformer.apply(saved));
    }

    @PutMapping(value = "/{teamId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TeamDto> updateTeam(@PathVariable final String teamId,
                                              @Validated({Default.class}) @RequestBody final TeamDto teamDto) {
        Assert.isTrue(teamId.equals(teamDto.getId()), "Ids don't match");
        final var saved = teamCommandService.save(teamDtoToExistingTeamTransformer.apply(teamId, teamDto));
        return ResponseEntity.ok(teamToTeamDtoTransformer.apply(saved));
    }

    @PatchMapping(value = "/{teamId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TeamDto> partialUpdateTeam(@PathVariable final String teamId,
                                                     @Validated(Default.class) @RequestBody final TeamDto teamDto) {
        final var updated = partialTeamDtoToTeamTransformer.apply(teamId, teamDto);
        return ResponseEntity.ok(teamToTeamDtoTransformer.apply(teamCommandService.save(updated)));


    }

}
