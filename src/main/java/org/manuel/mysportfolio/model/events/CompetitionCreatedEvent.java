package org.manuel.mysportfolio.model.events;

import io.github.manuelarte.mysportfolio.model.documents.competition.Competition;

public class CompetitionCreatedEvent extends MySportfolioEvent<Competition> {

  /**
   * Create a new CompetitionCreatedEvent.
   *
   * @param source the object on which the event initially occurred (never {@code null})
   */
  public CompetitionCreatedEvent(final Competition source) {
    super(source);
  }

}
