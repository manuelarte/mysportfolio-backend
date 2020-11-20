package org.manuel.mysportfolio.services;

import io.github.manuelarte.mysportfolio.model.documents.teamtouser.UserInTeam;
import java.time.LocalDate;
import java.util.function.Supplier;
import org.manuel.mysportfolio.config.SystemAuthentication;
import org.manuel.mysportfolio.model.entities.usernotification.TeamAddUserNotification;
import org.manuel.mysportfolio.services.command.TeamToUsersCommandService;
import org.manuel.mysportfolio.services.query.TeamToUsersQueryService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class TeamAddUserNotificationHandler {

  private final TeamToUsersQueryService teamToUsersQueryService;
  private final TeamToUsersCommandService teamToUsersCommandService;

  public void handleAccept(final TeamAddUserNotification teamAddUserNotification) {
    if (teamToUsersQueryService.findByTeamId(teamAddUserNotification.getTeamId())
        .filter(it -> it.getUsers().containsKey(teamAddUserNotification.getTo())).isEmpty()) {
      doWithSystemAuthentication(() -> teamToUsersCommandService
          .updateUserInTeam(teamAddUserNotification.getTeamId(), teamAddUserNotification.getTo(),
              new UserInTeam(LocalDate.now(), null, UserInTeam.UserInTeamRole.PLAYER)));
    }

  }

  public void handleReject(final TeamAddUserNotification teamAddUserNotification) {
    // do nothing
  }

  private <T> T doWithSystemAuthentication(final Supplier<T> action) {
    final Authentication previous = SecurityContextHolder.getContext().getAuthentication();
    try {
      SecurityContextHolder.getContext().setAuthentication(new SystemAuthentication());
      return action.get();
    } finally {
      SecurityContextHolder.getContext().setAuthentication(previous);
    }
  }
}
