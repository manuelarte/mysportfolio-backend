package org.manuel.mysportfolio.listeners;

import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.model.Badge;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.model.entities.badges.UserBadges;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.events.MatchCreatedEvent;
import org.manuel.mysportfolio.services.command.UserBadgesCommandService;
import org.springframework.context.ApplicationEvent;

class MatchCreatedEventListenerTest {

  private UserBadgesCommandService userBadgesCommandService;
  private UserIdProvider userIdProvider;
  private MatchCreatedEventListener matchCreatedEventListener;

  @BeforeEach
  public void setUp() {
    userIdProvider = () -> "123456789";
    userBadgesCommandService = new UserBadgesCommandService() {
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
    matchCreatedEventListener = new MatchCreatedEventListener(userBadgesCommandService, userIdProvider);
  }

  @Test
  public void testFirstFootballMatch() {
    final var match = new Match();
    match.setSport(Sport.FOOTBALL);
    match.setPlayedFor(Collections.singletonMap("123456789", TeamOption.HOME_TEAM));
    final var matchCreatedEvent = new MatchCreatedEvent(match);
    matchCreatedEventListener.onApplicationEvent(matchCreatedEvent);
  }

}
