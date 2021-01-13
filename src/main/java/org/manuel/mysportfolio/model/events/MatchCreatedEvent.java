package org.manuel.mysportfolio.model.events;

import io.github.manuelarte.mysportfolio.model.documents.match.Match;

public class MatchCreatedEvent extends MySportfolioEvent<Match<?, ?>> {

  /**
   * Create a new MatchCreatedEvent.
   *
   * @param source the object on which the event initially occurred (never {@code null})
   */
  public MatchCreatedEvent(final Match<?, ?> source) {
    super(source);
  }

}
