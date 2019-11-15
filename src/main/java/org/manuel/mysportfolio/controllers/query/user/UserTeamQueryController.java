package org.manuel.mysportfolio.controllers.query.user;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.dtos.teamtousers.UserInTeamDto;
import org.manuel.mysportfolio.model.dtos.user.UserTeamDto;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.manuel.mysportfolio.services.query.TeamToUsersQueryService;
import org.manuel.mysportfolio.transformers.team.TeamToTeamDtoTransformer;
import org.manuel.mysportfolio.transformers.teamtousers.UserInTeamToUserInTeamDtoTransformer;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users/me/teams")
@AllArgsConstructor
public class UserTeamQueryController {

    private final TeamQueryService teamQueryService;
    private final TeamToUsersQueryService teamToUsersQueryService;
    private final TeamToTeamDtoTransformer teamToTeamDtoTransformer;
    private final UserInTeamToUserInTeamDtoTransformer userInTeamToUserInTeamDtoTransformer;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UserTeamDto>> findAllMyTeams(@PageableDefault final Pageable pageable) {
        final Page<Team> teams = teamQueryService.findAllForUser(pageable, getUserId());
        final List<TeamToUsers> byTeamIdIn = teamToUsersQueryService.findByTeamIdIn(teams.stream().map(Team::getId).collect(Collectors.toList()));
        final var response = teams.map( t -> new UserTeamDto(teamToTeamDtoTransformer.apply(t), getUserInTeamDto(t.getId(), byTeamIdIn)) );
        return ResponseEntity.ok(response);
    }

    private UserInTeamDto getUserInTeamDto(final ObjectId teamId, final  List<TeamToUsers> byTeamIdIn) {
        return userInTeamToUserInTeamDtoTransformer.apply(byTeamIdIn.stream().filter(e -> e.getTeamId().equals(teamId)).findFirst()
                .map(tI -> tI.getUsers().get(getUserId()))
                .orElse(null));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTeamDto> findOne(@PathVariable final ObjectId id) {
        final Team team = teamQueryService.findOne(id).orElseThrow(() ->
                new EntityNotFoundException(Team.class, id.toString()));
        final UserInTeam userInTeam = teamToUsersQueryService.findByTeamId(id).map(u -> u.getUsers().get(getUserId())).orElse(null);
        return ResponseEntity.ok(new UserTeamDto(teamToTeamDtoTransformer.apply(team), userInTeamToUserInTeamDtoTransformer.apply(userInTeam)));
    }

    private String getUserId() {
        return ((DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getAttributes().get("sub").toString();
    }

}
