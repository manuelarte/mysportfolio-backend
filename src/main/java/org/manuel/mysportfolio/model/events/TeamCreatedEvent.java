package org.manuel.mysportfolio.model.events;

import io.github.manuelarte.mysportfolio.model.documents.team.Team;

public class TeamCreatedEvent extends MySportfolioEvent<Team> {

  /**
   * Create a new TeamCreatedEvent.
   *
   * @param source the object on which the event initially occurred (never {@code null})
   */
  public TeamCreatedEvent(final Team source) {
    super(source);
  }

}
