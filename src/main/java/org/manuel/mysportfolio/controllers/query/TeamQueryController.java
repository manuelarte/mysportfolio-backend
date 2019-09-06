package org.manuel.mysportfolio.controllers.query;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.manuel.mysportfolio.transformers.team.TeamToTeamDtoTransformer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/teams")
@AllArgsConstructor
public class TeamQueryController {

    private final TeamQueryService teamQueryService;
    private final TeamToTeamDtoTransformer teamToTeamDtoTransformer;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDto> findOne(@PathVariable final String id) {
        final Team team = teamQueryService.findOne(new ObjectId(id)).orElseThrow(() ->
                new IllegalArgumentException(String.format("Team with id %s not found", id)));
        return ResponseEntity.ok(teamToTeamDtoTransformer.apply(team));
    }

}
