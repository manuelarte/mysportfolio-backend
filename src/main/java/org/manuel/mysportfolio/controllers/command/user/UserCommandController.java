package org.manuel.mysportfolio.controllers.command.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.dtos.user.UserTeamDto;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.services.command.AppUserCommandService;
import org.manuel.mysportfolio.services.command.TeamCommandService;
import org.manuel.mysportfolio.services.command.TeamToUsersCommandService;
import org.manuel.mysportfolio.services.query.AppUserQueryService;
import org.manuel.mysportfolio.services.query.TeamToUsersQueryService;
import org.manuel.mysportfolio.transformers.team.TeamDtoToTeamTransformer;
import org.manuel.mysportfolio.transformers.team.TeamToTeamDtoTransformer;
import org.manuel.mysportfolio.transformers.teamtousers.UserInTeamDtoToUserInTeamTransformer;
import org.manuel.mysportfolio.transformers.teamtousers.UserInTeamToUserInTeamDtoTransformer;
import org.manuel.mysportfolio.validation.NewEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users/me")
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class UserCommandController {

    private final AppUserQueryService appUserQueryService;
    private final AppUserCommandService appUserCommandService;
    private final UserIdProvider userIdProvider;

    @PostMapping(value = "registration-token", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> updateRegistrationToken(@Valid @RequestBody final RegistrationTokenDto data) {
        final String userId = getUserId();
        final var user = appUserQueryService.findByExternalId(userId).orElseThrow(() -> new EntityNotFoundException(AppUser.class, userId));
        user.setRegistrationToken(data.registrationToken);
        appUserCommandService.save(user);
        log.info("Registration token received for user %s", userId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping(value = "notifications/{notificationId}")
    public ResponseEntity<Void> acceptNotification(@PathVariable final ObjectId notificationId) {
        appUserCommandService.acceptUserNotification(notificationId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping(value = "notifications/{notificationId}")
    public ResponseEntity<Void> rejectNotification(@PathVariable final ObjectId notificationId) {
        appUserCommandService.rejectUserNotification(notificationId);
        return ResponseEntity.accepted().build();
    }

    private String getUserId() {
        return userIdProvider.getUserId();
    }

    @JsonDeserialize(builder = RegistrationTokenDto.RegistrationTokenDtoBuilder.class)
    @lombok.AllArgsConstructor
    @lombok.Data
    @lombok.Builder(toBuilder = true)
    public static class RegistrationTokenDto {
        @NotNull
        private final String registrationToken;

        @JsonPOJOBuilder(withPrefix = "")
        public static final class RegistrationTokenDtoBuilder {}
    }


}
