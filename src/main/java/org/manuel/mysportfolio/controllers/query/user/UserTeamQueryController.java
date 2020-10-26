package org.manuel.mysportfolio.controllers.query.user;

import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.controllers.Util;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.dtos.teamtousers.UserInTeamDto;
import org.manuel.mysportfolio.model.dtos.user.UserTeamDto;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.services.query.AppUserQueryService;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.manuel.mysportfolio.services.query.TeamToUsersQueryService;
import org.manuel.mysportfolio.transformers.team.TeamToTeamDtoTransformer;
import org.manuel.mysportfolio.transformers.teamtousers.UserInTeamToUserInTeamDtoTransformer;
import org.manuel.mysportfolio.validations.UserExists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/{externalUserId}/teams")
@Validated
@lombok.AllArgsConstructor
public class UserTeamQueryController {

  private final AppUserQueryService appUserQueryService;
  private final TeamQueryService teamQueryService;
  private final TeamToUsersQueryService teamToUsersQueryService;
  private final TeamToTeamDtoTransformer teamToTeamDtoTransformer;
  private final UserInTeamToUserInTeamDtoTransformer userInTeamToUserInTeamDtoTransformer;
  private final UserIdProvider userIdProvider;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<UserTeamDto>> findAllMyTeams(
      @PathVariable @UserExists final String externalUserId,
      @PageableDefault final Pageable pageable) {
    final var appUser = Util.getUser(appUserQueryService, userIdProvider, externalUserId);
    final var teams = teamQueryService.findAllForUser(pageable, appUser.getExternalId());
    final var byTeamIdIn = teamToUsersQueryService
        .findByTeamIdIn(teams.stream().map(Team::getId).collect(Collectors.toList()));
    final var response = teams.map(t -> new UserTeamDto(teamToTeamDtoTransformer.apply(t),
        getUserInTeamDto(t.getId(), byTeamIdIn, appUser)));
    return ResponseEntity.ok(response);
  }

  private UserInTeamDto getUserInTeamDto(final ObjectId teamId, final List<TeamToUsers> byTeamIdIn,
      final AppUser appUser) {
    return userInTeamToUserInTeamDtoTransformer
        .apply(byTeamIdIn.stream().filter(e -> e.getTeamId().equals(teamId)).findFirst()
            .map(it -> it.getUsers().get(appUser.getExternalId()))
            .orElse(null));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserTeamDto> findOne(
      @PathVariable String externalUserId,
      @PathVariable final ObjectId id) {
    final var appUser = Util.getUser(appUserQueryService, userIdProvider, externalUserId);
    final var team = teamQueryService.findOne(id).orElseThrow(() ->
        new EntityNotFoundException(Team.class, id.toString()));
    final var userInTeam = teamToUsersQueryService.findByTeamId(id)
        .map(u -> u.getUsers().get(appUser.getExternalId())).orElse(null);
    return ResponseEntity.ok(new UserTeamDto(teamToTeamDtoTransformer.apply(team),
        userInTeamToUserInTeamDtoTransformer.apply(userInTeam)));
  }

}
