package org.manuel.mysportfolio;

import io.github.manuelarte.mysportfolio.model.documents.Competition;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.manuel.mysportfolio.config.SystemAuthentication;
import org.manuel.mysportfolio.exceptions.NotificationException;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.model.notifications.NotificationsFactory;
import org.manuel.mysportfolio.services.FirebaseNotificationService;
import org.manuel.mysportfolio.services.query.AppUserQueryService;
import org.manuel.mysportfolio.services.query.CompetitionQueryService;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class ScheduledTask {

  private final CompetitionQueryService competitionQueryService;
  private final AppUserQueryService appUserQueryService;
  private final FirebaseNotificationService notificationsService;

  //@Scheduled(
  //        cron = "0 0 21 ? * *"
  //fixedRate = 1 * 60 * 60 * 24 * 1000
  //)
  public void sendCompetitionDay() {
    final DayOfWeek from = DayOfWeek.from(LocalDate.now()).minus(1);
    final List<Competition> competitionsPlayedToday = doWithSystemAuthentication(
        () -> competitionQueryService.findAllPlaying(from));
    final AtomicInteger numberSent = new AtomicInteger(0);
    final Map<String, AppUser> users = appUserQueryService
        .findByExternalIds(competitionsPlayedToday.stream().map(Competition::getCreatedBy)
            .map(Optional::get).collect(Collectors.toList())).stream()
        .collect(Collectors.toMap(AppUser::getExternalId, Function.identity()));
    // TODO, filter if the user submitted today a match with the same competition
    competitionsPlayedToday.stream().filter(
        it -> users.containsKey(it.getCreatedBy().get()) && users.get(it.getCreatedBy().get())
            .getSettings().getReceiveCompetitionsNotifications())
        .forEach(
            it -> {
              final var message = NotificationsFactory.createCompetitionsReminderNotification(
                  users.get(it.getCreatedBy().get()).getRegistrationToken(), it);
              try {
                notificationsService.send(message);
                numberSent.incrementAndGet();
              } catch (final NotificationException e) {
                log.error("Could not send sendCompetitionDay message", e);
              }
            }
        );
    log.info("{}/{} Competitions day notifications sent: {}", competitionsPlayedToday.size(),
        numberSent.get(), LocalDate.now());

  }

  private <T> T doWithSystemAuthentication(final Supplier<T> action) {
    try {
      SecurityContextHolder.getContext().setAuthentication(new SystemAuthentication());
      return action.get();
    } finally {
      SecurityContextHolder.clearContext();
    }
  }
}
