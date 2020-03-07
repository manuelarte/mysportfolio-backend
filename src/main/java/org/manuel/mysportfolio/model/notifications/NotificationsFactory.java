package org.manuel.mysportfolio.model.notifications;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.experimental.UtilityClass;
import org.manuel.mysportfolio.model.entities.Competition;

@UtilityClass
public class NotificationsFactory {

  public static Message createCompetitionsReminderNotification(final String registrationToken,
      final Competition competition) {
    final String notificationBody = String
        .format("Did you play today?, don't forget to register your match for %s",
            competition.getName());

    return Message.builder()
        .setNotification(Notification.builder()
            .setTitle("Competition day")
            .setBody(notificationBody)
            .build())
        .setToken(registrationToken)
        .putData("type", NOTIFICATION_TYPE.COMPETITION_DAY.toString())
        .putData("competitionId", competition.getId().toString())
        .putData("sport", competition.getSport().toString())
        .setAndroidConfig(AndroidConfig.builder()
            .setTtl(3600 * 1000)
            .build())
        .build();

  }

  public enum NOTIFICATION_TYPE {
    COMPETITION_DAY
  }
}
