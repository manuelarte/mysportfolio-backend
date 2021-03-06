package org.manuel.mysportfolio.controllers.query.user;

import io.github.manuelarte.mysportfolio.exceptions.EntityNotFoundException;
import io.github.manuelarte.mysportfolio.model.documents.team.Team;
import io.github.manuelarte.mysportfolio.model.documents.teamtouser.TeamToUsers;
import io.github.manuelarte.mysportfolio.model.documents.teamtouser.UserInTeam;
import io.github.manuelarte.mysportfolio.model.documents.user.AppUser;
import io.github.manuelarte.mysportfolio.model.dtos.teamtousers.UserInTeamDto;
import io.github.manuelarte.mysportfolio.model.dtos.user.UserTeamDto;
import io.github.manuelarte.spring.queryparameter.QueryParameter;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.controllers.Util;
import org.manuel.mysportfolio.services.query.AppUserQueryService;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.manuel.mysportfolio.services.query.TeamToUsersQueryService;
import org.manuel.mysportfolio.transformers.team.TeamToTeamDtoTransformer;
import org.manuel.mysportfolio.transformers.teamtousers.UserInTeamToUserInTeamDtoTransformer;
import org.manuel.mysportfolio.validations.UserExists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
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
      @PageableDefault final Pageable pageable,
      @QueryParameter(entity = UserInTeam.class, allowedKeys = "to") final Query q) {
    final var appUser = Util.getUser(appUserQueryService, userIdProvider, externalUserId);
    final var teamsToUser = teamToUsersQueryService.findAllByUserExists(pageable, appUser.getExternalId());
    final var teams = teamQueryService.findAllByIdsIn(pageable,
        teamsToUser.stream().map(TeamToUsers::getTeamId).collect(Collectors.toSet()));
    final var response = teams.map(t -> new UserTeamDto(teamToTeamDtoTransformer.apply(t),
        getUserInTeamDto(t.getId(), teamsToUser.getContent(), appUser)));
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
