package org.manuel.mysportfolio.model.events;

import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.model.notifications.NewBadgesNotification.Reason;
import org.manuel.mysportfolio.model.notifications.NewBadgesNotification.Reason.Entity;

public class TeamCreatedEvent extends MySportfolioEvent<Team> {

  /**
   * Create a new TeamCreatedEvent.
   *
   * @param source the object on which the event initially occurred (never {@code null})
   */
  public TeamCreatedEvent(final Team source) {
    super(source);
  }

  @Override
  public Reason getNewBadgesNotificationReason() {
    return new Reason(Entity.TEAM, ((Team)source).getId().toString());
  }

}
