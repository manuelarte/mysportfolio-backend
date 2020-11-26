package org.manuel.mysportfolio.controllers.query;

import io.github.manuelarte.mysportfolio.exceptions.EntityNotFoundException;
import io.github.manuelarte.mysportfolio.model.documents.teamtouser.TeamToUsers;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.teamtousers.TeamToUsersDto;
import org.manuel.mysportfolio.services.query.TeamToUsersQueryService;
import org.manuel.mysportfolio.transformers.teamtousers.TeamToUsersToTeamToUsersDtoTransformer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/teams/{teamId}/users")
@lombok.AllArgsConstructor
public class TeamToUsersQueryController {

  private final TeamToUsersQueryService teamToUsersQueryService;
  private final TeamToUsersToTeamToUsersDtoTransformer teamToUsersToTeamToUsersDtoTransformer;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TeamToUsersDto> findTeamsToUsersForTeam(
      @PathVariable final ObjectId teamId) {
    final var teamToUsers = teamToUsersQueryService.findByTeamId(teamId)
        .orElseThrow(() -> new EntityNotFoundException(TeamToUsers.class, teamId.toString()));
    return ResponseEntity.ok(teamToUsersToTeamToUsersDtoTransformer.apply(teamToUsers));
  }

}
