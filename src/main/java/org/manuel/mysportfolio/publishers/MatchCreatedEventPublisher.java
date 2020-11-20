package org.manuel.mysportfolio.publishers;

import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import org.manuel.mysportfolio.model.events.MatchCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class MatchCreatedEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publishEvent(final Match<?, ?> createdMatch) {
    log.info("Publishing match created event for match {}.", createdMatch.getId().toString());
    final MatchCreatedEvent teamCreatedEvent = new MatchCreatedEvent(createdMatch);
    applicationEventPublisher.publishEvent(teamCreatedEvent);
  }

}
