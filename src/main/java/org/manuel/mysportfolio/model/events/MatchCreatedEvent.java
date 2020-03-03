package org.manuel.mysportfolio.model.events;

import org.manuel.mysportfolio.model.entities.match.Match;

public class MatchCreatedEvent extends SportEvent<Match<?, ?>> {

  /**
   * Create a new MatchCreatedEvent.
   *
   * @param source the object on which the event initially occurred (never {@code null})
   */
  public MatchCreatedEvent(final Match<?, ?> source) {
    super(source);
  }
}
