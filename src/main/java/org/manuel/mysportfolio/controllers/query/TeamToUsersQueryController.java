package org.manuel.mysportfolio.controllers.query;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
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
@AllArgsConstructor
public class TeamToUsersQueryController {

    private final TeamToUsersQueryService teamToUsersQueryService;
    private final TeamToUsersToTeamToUsersDtoTransformer teamToUsersToTeamToUsersDtoTransformer;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamToUsersDto> findTeamsToUsersForTeam(@PathVariable final ObjectId teamId) {
        final var teamToUsers = teamToUsersQueryService.findByTeamId(teamId).orElseThrow(() -> new EntityNotFoundException(
                String.format("TeamToUsers entity not found for team id: %s", teamId.toString())));
        return ResponseEntity.ok(teamToUsersToTeamToUsersDtoTransformer.apply(teamToUsers));
    }

}
