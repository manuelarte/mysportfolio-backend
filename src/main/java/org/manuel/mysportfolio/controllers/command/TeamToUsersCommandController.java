package org.manuel.mysportfolio.controllers.command;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.teamtousers.TeamToUsersDto;
import org.manuel.mysportfolio.model.dtos.teamtousers.UserInTeamDto;
import org.manuel.mysportfolio.services.command.TeamToUsersCommandService;
import org.manuel.mysportfolio.transformers.teamtousers.TeamToUsersDtoToTeamToUsersTransformer;
import org.manuel.mysportfolio.transformers.teamtousers.TeamToUsersToTeamToUsersDtoTransformer;
import org.manuel.mysportfolio.transformers.teamtousers.UserInTeamDtoToUserInTeamTransformer;
import org.manuel.mysportfolio.transformers.teamtousers.UserInTeamToUserInTeamDtoTransformer;
import org.manuel.mysportfolio.validation.NewEntity;
import org.manuel.mysportfolio.validation.PartialUpdateEntity;
import org.manuel.mysportfolio.validation.UpdateEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.groups.Default;

@RestController
@RequestMapping("/api/v1/teams/{teamId}/users")
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class TeamToUsersCommandController {

    private final TeamToUsersCommandService teamToUsersCommandService;
    private final TeamToUsersDtoToTeamToUsersTransformer teamToUsersDtoToTeamToUsersTransformer;
    private final TeamToUsersToTeamToUsersDtoTransformer teamToUsersToTeamToUsersDtoTransformer;
    private final UserInTeamDtoToUserInTeamTransformer userInTeamDtoToUserInTeamTransformer;
    private final UserInTeamToUserInTeamDtoTransformer userInTeamToUserInTeamDtoTransformer;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TeamToUsersDto> saveTeamToUsers(
            @PathVariable final ObjectId teamId,
            @Validated({Default.class, NewEntity.class}) @RequestBody final TeamToUsersDto teamToUsersDto) {
        final var saved = teamToUsersCommandService.save(teamToUsersDtoToTeamToUsersTransformer.apply(teamId, teamToUsersDto));
        final var location = ServletUriComponentsBuilder
                .fromCurrentRequest().build().toUri();
        log.info("Users in Team with id {}, created by {}", saved.getId(), getUserId());
        return ResponseEntity.created(location).body(teamToUsersToTeamToUsersDtoTransformer.apply(saved));
    }

    /*
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TeamDto> updateTeamToUsers(@PathVariable final String teamId,
                                              @Validated({Default.class}) @RequestBody final TeamDto teamDto) {
        Assert.isTrue(teamId.equals(teamDto.getId()), "Ids don't match");
        final var saved = teamCommandService.update(teamDtoToExistingTeamTransformer.apply(teamId, teamDto));
        return ResponseEntity.ok(teamToTeamDtoTransformer.apply(saved));
    }
     */

    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserInTeamDto> updateUserInTeam(@PathVariable final ObjectId teamId,
                                                          @PathVariable final String userId,
                                                          @Validated({ Default.class, UpdateEntity.class} ) @RequestBody final UserInTeamDto userInTeamDto) {
        final var entity = userInTeamDtoToUserInTeamTransformer.apply(userInTeamDto);
        return ResponseEntity.ok(userInTeamToUserInTeamDtoTransformer.apply(teamToUsersCommandService.updateUserInTeam(teamId, userId, entity)));
    }

    private String getUserId() {
        return ((DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getAttributes().get("sub").toString();
    }

}
