package org.manuel.mysportfolio.controllers.command.user;

import io.github.manuelarte.mysportfolio.model.documents.teamtouser.UserInTeam;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.New;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.Update;
import javax.validation.groups.Default;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.model.dtos.user.UserTeamDto;
import org.manuel.mysportfolio.services.command.TeamCommandService;
import org.manuel.mysportfolio.services.command.TeamToUsersCommandService;
import org.manuel.mysportfolio.services.query.TeamToUsersQueryService;
import org.manuel.mysportfolio.transformers.team.TeamDtoToTeamTransformer;
import org.manuel.mysportfolio.transformers.team.TeamToTeamDtoTransformer;
import org.manuel.mysportfolio.transformers.teamtousers.UserInTeamDtoToUserInTeamTransformer;
import org.manuel.mysportfolio.transformers.teamtousers.UserInTeamToUserInTeamDtoTransformer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/users/me/teams")
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class UserTeamCommandController {

  private final TeamCommandService teamCommandService;
  private final TeamToUsersQueryService teamToUsersQueryService;
  private final TeamToUsersCommandService teamToUsersCommandService;
  private final TeamDtoToTeamTransformer teamDtoToTeamTransformer;
  private final TeamToTeamDtoTransformer teamToTeamDtoTransformer;
  private final UserInTeamToUserInTeamDtoTransformer userInTeamToUserInTeamDtoTransformer;
  private final UserInTeamDtoToUserInTeamTransformer userInTeamDtoToUserInTeamTransformer;
  private final UserIdProvider userIdProvider;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserTeamDto> saveUserTeam(
      @Validated({Default.class, New.class}) @RequestBody final UserTeamDto userTeamDto) {
    final var teamSaved = teamCommandService
        .save(teamDtoToTeamTransformer.apply(userTeamDto.getTeam()));
    final UserInTeam userInTeamSaved;
    if (userTeamDto.getUserInTeam() != null) {
      userInTeamSaved = teamToUsersCommandService.updateUserInTeam(teamSaved.getId(), getUserId(),
          userInTeamDtoToUserInTeamTransformer.apply(userTeamDto.getUserInTeam()));
    } else {
      userInTeamSaved = teamToUsersQueryService.findByTeamId(teamSaved.getId()).get().getUsers()
          .get(getUserId());
    }
    final var location = ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(teamSaved.getId()).toUri();
    log.info("Team with id {}, created by {}, saved", teamSaved.getId(), teamSaved.getCreatedBy());
    return ResponseEntity.created(location)
        .body(new UserTeamDto(teamToTeamDtoTransformer.apply(teamSaved),
            userInTeamToUserInTeamDtoTransformer.apply(userInTeamSaved)));
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserTeamDto> updateUserTeam(
      @Validated({Default.class, Update.class}) @RequestBody final UserTeamDto userTeamDto) {
    final var teamUpdated = teamCommandService
        .update(teamDtoToTeamTransformer.apply(userTeamDto.getTeam()));
    final UserInTeam userInTeamSaved;
    if (userTeamDto.getUserInTeam() != null) {
      userInTeamSaved = teamToUsersCommandService.updateUserInTeam(teamUpdated.getId(), getUserId(),
          userInTeamDtoToUserInTeamTransformer.apply(userTeamDto.getUserInTeam()));
    } else {
      userInTeamSaved = teamToUsersQueryService.findByTeamId(teamUpdated.getId()).get().getUsers()
          .get(getUserId());
    }
    return ResponseEntity.ok(new UserTeamDto(teamToTeamDtoTransformer.apply(teamUpdated),
        userInTeamToUserInTeamDtoTransformer.apply(userInTeamSaved)));
  }

  private String getUserId() {
    return userIdProvider.getUserId();
  }


}
