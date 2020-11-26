package org.manuel.mysportfolio.controllers.command.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.mysportfolio.exceptions.EntityNotFoundException;
import io.github.manuelarte.mysportfolio.model.documents.user.AppUser;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.services.command.AppUserCommandService;
import org.manuel.mysportfolio.services.query.AppUserQueryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/me")
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class UserCommandController {

  private final AppUserQueryService appUserQueryService;
  private final AppUserCommandService appUserCommandService;
  private final UserIdProvider userIdProvider;

  @PostMapping(value = "registration-token", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> updateRegistrationToken(
      @Valid @RequestBody final RegistrationTokenDto data) {
    final String userId = getUserId();
    final var user = appUserQueryService.findByExternalId(userId)
        .orElseThrow(() -> new EntityNotFoundException(AppUser.class, userId));
    user.setRegistrationToken(data.registrationToken);
    appUserCommandService.save(user);
    log.info("Registration token received for user {}", userId);
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
    public static final class RegistrationTokenDtoBuilder {

    }
  }


}
