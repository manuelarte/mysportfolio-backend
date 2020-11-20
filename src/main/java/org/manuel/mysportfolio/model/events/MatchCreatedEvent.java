package org.manuel.mysportfolio.model.events;

import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import org.manuel.mysportfolio.model.notifications.NewBadgesNotification.Reason;
import org.manuel.mysportfolio.model.notifications.NewBadgesNotification.Reason.Entity;

public class MatchCreatedEvent extends MySportfolioEvent<Match<?, ?>> {

  /**
   * Create a new MatchCreatedEvent.
   *
   * @param source the object on which the event initially occurred (never {@code null})
   */
  public MatchCreatedEvent(final Match<?, ?> source) {
    super(source);
  }

  @Override
  public Reason getNewBadgesNotificationReason() {
    return new Reason(Entity.MATCH, ((Match<?, ?>)source).getId().toString());
  }

}
