package org.manuel.mysportfolio.controllers.command;

import io.github.manuelarte.mysportfolio.model.documents.team.Team;
import io.github.manuelarte.spring.manuelartevalidation.constraints.Exists;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.New;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.PartialUpdate;
import io.jsonwebtoken.lang.Assert;
import javax.validation.groups.Default;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.services.command.TeamCommandService;
import org.manuel.mysportfolio.transformers.team.TeamDtoToExistingTeamTransformer;
import org.manuel.mysportfolio.transformers.team.TeamDtoToTeamTransformer;
import org.manuel.mysportfolio.transformers.team.TeamToTeamDtoTransformer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/teams")
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class TeamCommandController {

  private final TeamCommandService teamCommandService;
  private final TeamDtoToTeamTransformer teamDtoToTeamTransformer;
  private final TeamToTeamDtoTransformer teamToTeamDtoTransformer;
  private final TeamDtoToExistingTeamTransformer teamDtoToExistingTeamTransformer;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TeamDto> saveTeam(
      @Validated({Default.class, New.class}) @RequestBody final TeamDto teamDto) {
    final var saved = teamCommandService.save(teamDtoToTeamTransformer.apply(teamDto));
    final var location = ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(saved.getId()).toUri();
    log.info("Team with id {}, created by {} saved", saved.getId(), saved.getCreatedBy());
    return ResponseEntity.created(location).body(teamToTeamDtoTransformer.apply(saved));
  }

  @PutMapping(value = "/{teamId}", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TeamDto> updateTeam(@PathVariable final String teamId,
      @Validated({Default.class}) @RequestBody final TeamDto teamDto) {
    Assert.isTrue(teamId.equals(teamDto.getId()), "Ids don't match");
    final var saved = teamCommandService
        .update(teamDtoToExistingTeamTransformer.apply(teamId, teamDto));
    return ResponseEntity.ok(teamToTeamDtoTransformer.apply(saved));
  }

  @PatchMapping(value = "/{teamId}", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TeamDto> partialUpdateTeam(
      @PathVariable @Exists(Team.class) final String teamId,
      @Validated({Default.class, PartialUpdate.class}) @RequestBody final TeamDto teamDto) {
    final var partialTeam = teamDtoToTeamTransformer.apply(teamDto);
    return ResponseEntity
        .ok(teamToTeamDtoTransformer.apply(teamCommandService.partialUpdate(new ObjectId(teamId), partialTeam)));
  }

}
