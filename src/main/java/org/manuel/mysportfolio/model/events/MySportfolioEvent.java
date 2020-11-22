package org.manuel.mysportfolio.model.events;

import org.manuel.mysportfolio.model.notifications.NewBadgesNotification;
import org.springframework.context.ApplicationEvent;

public abstract class MySportfolioEvent<T> extends ApplicationEvent {

  /**
   * Create a new {@code ApplicationEvent}.
   *
   * @param source the object on which the event initially occurred or with which the event is
   *               associated (never {@code null})
   */
  public MySportfolioEvent(T source) {
    super(source);
  }

  public abstract NewBadgesNotification.Reason getNewBadgesNotificationReason();


}
