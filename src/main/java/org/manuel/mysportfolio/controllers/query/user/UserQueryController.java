package org.manuel.mysportfolio.controllers.query.user;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import java.util.stream.Collectors;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.model.dtos.user.UserRecordDto;
import org.manuel.mysportfolio.model.dtos.usernotification.UserNotificationDto;
import org.manuel.mysportfolio.model.entities.usernotification.UserNotification;
import org.manuel.mysportfolio.services.query.AppUserQueryService;
import org.manuel.mysportfolio.services.query.FirebaseQueryService;
import org.manuel.mysportfolio.transformers.usernotification.UserNotificationToUserNotificationDtoTransformer;
import org.manuel.mysportfolio.transformers.users.UserRecordToUserRecordDtoTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/")
@lombok.AllArgsConstructor
public class UserQueryController {

  private final AppUserQueryService appUserQueryService;
  private final FirebaseQueryService firebaseQueryService;
  private final UserNotificationToUserNotificationDtoTransformer userNotificationToUserNotificationDtoTransformer;
  private final UserRecordToUserRecordDtoTransformer userRecordToUserRecordDtoTransformer;
  private final UserIdProvider userIdProvider;

  @GetMapping(value = "/me/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserNotificationPage> getMyNotifications(
      @PageableDefault final Pageable pageable) {
    final var userNotifications = appUserQueryService
        .getUserNotifications(pageable, userIdProvider.getUserId());
    final var page = new UserNotificationPage(userNotifications);
    return ResponseEntity.ok(page);
  }

  @GetMapping(value = "/{appUserExternalId}/userRecord", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserRecordDto> getUserRecord(@PathVariable final String appUserExternalId) throws FirebaseAuthException {
    final UserRecord userRecord = firebaseQueryService.findByFirebaseId(appUserExternalId);
    return ResponseEntity.ok(userRecordToUserRecordDtoTransformer.apply(userRecord));
  }

  private final class UserNotificationPage extends PageImpl<UserNotificationDto> {

    public UserNotificationPage(final Page<UserNotification> userNotifications) {
      super(userNotifications.getContent().stream()
              .map(userNotificationToUserNotificationDtoTransformer).collect(Collectors.toList()),
          userNotifications.getPageable(), userNotifications.getTotalElements());
    }
  }

}
