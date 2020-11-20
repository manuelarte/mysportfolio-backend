package org.manuel.mysportfolio.model.events;

import io.github.manuelarte.mysportfolio.model.documents.Competition;
import org.manuel.mysportfolio.model.notifications.NewBadgesNotification.Reason;
import org.manuel.mysportfolio.model.notifications.NewBadgesNotification.Reason.Entity;

public class CompetitionCreatedEvent extends MySportfolioEvent<Competition> {

  /**
   * Create a new CompetitionCreatedEvent.
   *
   * @param source the object on which the event initially occurred (never {@code null})
   */
  public CompetitionCreatedEvent(final Competition source) {
    super(source);
  }

  @Override
  public Reason getNewBadgesNotificationReason() {
    return new Reason(Entity.COMPETITION, ((Competition)source).getId().toString());
  }

}
