package org.manuel.mysportfolio.controllers.query;

import io.github.manuelarte.mysportfolio.exceptions.EntityNotFoundException;
import io.github.manuelarte.mysportfolio.model.documents.team.Team;
import io.github.manuelarte.mysportfolio.model.dtos.team.TeamDto;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.manuel.mysportfolio.transformers.team.TeamToTeamDtoTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/teams")
@lombok.AllArgsConstructor
public class TeamQueryController {

  private final TeamQueryService teamQueryService;
  private final TeamToTeamDtoTransformer teamToTeamDtoTransformer;
  private final UserIdProvider userIdProvider;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<TeamDto>> findAllTeamsOfUser(
      @PageableDefault final Pageable pageable) {
    final var teams = teamQueryService.findAllForUser(pageable, getUserId());
    return ResponseEntity.ok(teams.map(teamToTeamDtoTransformer));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TeamDto> findOne(@PathVariable final ObjectId id) {
    final var team = teamQueryService.findOne(id).orElseThrow(() ->
        new EntityNotFoundException(Team.class, id.toString()));
    return ResponseEntity.ok(teamToTeamDtoTransformer.apply(team));
  }

  private String getUserId() {
    return userIdProvider.getUserId().orElse(null);
  }

}
