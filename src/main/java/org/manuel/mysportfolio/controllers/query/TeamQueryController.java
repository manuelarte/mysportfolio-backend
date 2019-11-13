package org.manuel.mysportfolio.controllers.query;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.manuel.mysportfolio.transformers.team.TeamToTeamDtoTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<TeamDto>> findAllTeamsCreatedByMe(@PageableDefault final Pageable pageable) {
        final Page<Team> teams = teamQueryService.findAllForUser(pageable, getUserId());
        return ResponseEntity.ok(teams.map(teamToTeamDtoTransformer));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDto> findOne(@PathVariable final ObjectId id) {
        final Team team = teamQueryService.findOne(id).orElseThrow(() ->
                new EntityNotFoundException(Team.class, id.toString()));
        return ResponseEntity.ok(teamToTeamDtoTransformer.apply(team));
    }

    private String getUserId() {
        return ((DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getAttributes().get("sub").toString();
    }

}
