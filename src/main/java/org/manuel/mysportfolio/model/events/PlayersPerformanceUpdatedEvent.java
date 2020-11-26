package org.manuel.mysportfolio.model.events;

import io.github.manuelarte.mysportfolio.model.documents.playersperformance.PlayersPerformance;
import org.manuel.mysportfolio.model.notifications.NewBadgesNotification.Reason;
import org.manuel.mysportfolio.model.notifications.NewBadgesNotification.Reason.Entity;

public class PlayersPerformanceUpdatedEvent extends MySportfolioEvent<PlayersPerformance> {

  /**
   * Create a new PlayersPerformanceUpdatedEvent.
   *
   * @param source the object on which the event initially occurred (never {@code null})
   */
  public PlayersPerformanceUpdatedEvent(final PlayersPerformance source) {
    super(source);
  }

  @Override
  public Reason getNewBadgesNotificationReason() {
    return new Reason(Entity.PLAYER_PERFORMANCE, ((PlayersPerformance)source).getId().toString());
  }

}
