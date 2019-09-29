package org.manuel.mysportfolio.controllers.command;

import lombok.AllArgsConstructor;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.services.command.TeamCommandService;
import org.manuel.mysportfolio.transformers.team.TeamDtoToTeamTransformer;
import org.manuel.mysportfolio.transformers.team.TeamToTeamDtoTransformer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/teams")
@AllArgsConstructor
public class TeamCommandController {

    private final TeamCommandService teamCommandService;
    private final TeamDtoToTeamTransformer teamDtoToTeamTransformer;
    private final TeamToTeamDtoTransformer teamToTeamDtoTransformer;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TeamDto> saveTeam(@Valid @RequestBody final TeamDto teamDto) {
        final var saved = teamCommandService.save(teamDtoToTeamTransformer.apply(teamDto));
        final var location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(teamToTeamDtoTransformer.apply(saved));


    }

}
