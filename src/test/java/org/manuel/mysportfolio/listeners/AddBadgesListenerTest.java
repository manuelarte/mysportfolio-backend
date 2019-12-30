package org.manuel.mysportfolio.listeners;

import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.model.Badge;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.model.entities.badges.UserBadges;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.events.MatchCreatedEvent;
import org.manuel.mysportfolio.services.command.UserBadgesCommandService;
import org.springframework.context.ApplicationEvent;

class AddBadgesListenerTest {

  private static final AddBadgesListener ADD_BADGES_LISTENER =
      new AddBadgesListener(userBadgesCommandService(), () -> "123456789");

  private static UserBadgesCommandService userBadgesCommandService() {
    return new UserBadgesCommandService() {
      @Override
      public UserBadges save(UserBadges userBadges) {
        return null;
      }

      @Override
      public UserBadges addBadges(String userId, Set<Badge> badges) {
        return null;
      }

      @Override
      public <T extends ApplicationEvent> UserBadges addBadges(String userId, T event) {
        return null;
      }
    };
  }

  @Test
  public void testFirstFootballMatch() {
    final Match<?, ?> match = new Match<>();
    match.setSport(Sport.FOOTBALL);
    match.setPlayedFor(Collections.singletonMap("123456789", TeamOption.HOME_TEAM));
    final var matchCreatedEvent = new MatchCreatedEvent(match);
    ADD_BADGES_LISTENER.onApplicationEvent(matchCreatedEvent);
  }

}
