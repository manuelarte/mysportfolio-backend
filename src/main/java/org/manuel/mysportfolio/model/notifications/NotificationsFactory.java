package org.manuel.mysportfolio.model.notifications;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import io.github.manuelarte.mysportfolio.model.documents.competition.Competition;
import java.util.Set;
import java.util.stream.Collectors;
import org.manuel.mysportfolio.model.Badge;
import org.manuel.mysportfolio.model.events.MySportfolioEvent;
import org.manuel.mysportfolio.transformers.BadgeToAppBadgeTransformer;
import org.springframework.stereotype.Component;

@Component
@lombok.RequiredArgsConstructor
public class NotificationsFactory {

  private final ObjectMapper objectMapper;
  private final BadgeToAppBadgeTransformer badgeToAppBadgeTransformer;

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
        .putData("type", NotificationType.COMPETITION_DAY.toString())
        .putData("competitionId", competition.getId().toString())
        .putData("sport", competition.getSport().toString())
        .setAndroidConfig(AndroidConfig.builder()
            .setTtl(3600 * 1000)
            .build())
        .build();

  }

  @lombok.SneakyThrows
  public Message createNewBadgesNotification(final String registrationToken,
      final Set<Badge> newBadges, final MySportfolioEvent<?> mySportfolioEvent) {

    final var payload = NewBadgesNotification.builder()
        .badges(newBadges.stream().map(badgeToAppBadgeTransformer).collect(Collectors.toSet()))
        .reason(mySportfolioEvent.getNewBadgesNotificationReason())
        .build();

    final String notificationBody = String
        .format("You achieved these new badges %s",
            newBadges.stream().map(Enum::toString).collect(Collectors.joining(", ")));

    return Message.builder()
        .setNotification(Notification.builder()
            .setTitle("New badges")
            .setBody(notificationBody)
            .build())
        .setToken(registrationToken)
        .putData("notificationType", NotificationType.USER_NOTIFICATION.notificationType)
        .putData("version", "v1")
        .putData("payload", objectMapper.writeValueAsString(payload))
        .setAndroidConfig(AndroidConfig.builder()
            .setTtl(3600 * 1000)
            .build())
        .build();
  }

  public enum NotificationType {
    COMPETITION_DAY("competition-day"), USER_NOTIFICATION("user-notification");

    String notificationType;
    NotificationType(final String notificationType) {
      this.notificationType = notificationType;
    }
  }
}
