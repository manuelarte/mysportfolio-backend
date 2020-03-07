package org.manuel.mysportfolio.services;

import org.manuel.mysportfolio.model.entities.usernotification.TeamAddUserNotification;
import org.manuel.mysportfolio.model.entities.usernotification.UserNotification;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class UserNotificationHandler {

  private final TeamAddUserNotificationHandler teamAddUserNotificationHandler;

  public void acceptNotification(final UserNotification userNotification) {
    if (userNotification instanceof TeamAddUserNotification) {
      teamAddUserNotificationHandler.handleAccept((TeamAddUserNotification) userNotification);
    }
  }

  public void rejectNotification(final UserNotification userNotification) {
    if (userNotification instanceof TeamAddUserNotification) {
      teamAddUserNotificationHandler.handleReject((TeamAddUserNotification) userNotification);
    }
  }
}
