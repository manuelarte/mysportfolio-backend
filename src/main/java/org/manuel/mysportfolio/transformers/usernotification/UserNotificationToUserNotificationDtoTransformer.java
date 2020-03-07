package org.manuel.mysportfolio.transformers.usernotification;

import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.usernotification.UserNotificationDto;
import org.manuel.mysportfolio.model.entities.usernotification.TeamAddUserNotification;
import org.manuel.mysportfolio.model.entities.usernotification.UserNotification;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class UserNotificationToUserNotificationDtoTransformer implements
    Function<UserNotification, UserNotificationDto> {

  private final TeamAddUserNotificationToTeamAddUserNotificationDtoTransformer teamAddUserNotificationToTeamAddUserNotificationDtoTransformer;

    @Override
    public UserNotificationDto apply(final UserNotification userNotification) {
        if (userNotification instanceof TeamAddUserNotification) {
            return teamAddUserNotificationToTeamAddUserNotificationDtoTransformer
                .apply((TeamAddUserNotification) userNotification);
        }
        return null;
    }

}
