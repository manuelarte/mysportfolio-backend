package org.manuel.mysportfolio.controllers.query.user;

import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.model.dtos.usernotification.UserNotificationDto;
import org.manuel.mysportfolio.model.entities.usernotification.UserNotification;
import org.manuel.mysportfolio.services.query.AppUserQueryService;
import org.manuel.mysportfolio.transformers.usernotification.UserNotificationToUserNotificationDtoTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users/me")
@lombok.AllArgsConstructor
public class UserQueryController {

    private final AppUserQueryService appUserQueryService;
    private final UserNotificationToUserNotificationDtoTransformer userNotificationToUserNotificationDtoTransformer;
    private final UserIdProvider userIdProvider;

    @GetMapping(value = "/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserNotificationPage> getMyNotifications(@PageableDefault final Pageable pageable) {
        final var userNotifications = appUserQueryService.getUserNotifications(pageable, userIdProvider.getUserId());
        final var page = new UserNotificationPage(userNotifications);
        return ResponseEntity.ok(page);
    }

    private final class UserNotificationPage extends PageImpl<UserNotificationDto> {
        public UserNotificationPage(final Page<UserNotification> userNotifications) {
            super(userNotifications.getContent().stream().map(userNotificationToUserNotificationDtoTransformer).collect(Collectors.toList()),
                    userNotifications.getPageable(), userNotifications.getTotalElements());
        }
    }

}
