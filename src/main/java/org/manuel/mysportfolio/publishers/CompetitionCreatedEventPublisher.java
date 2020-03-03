package org.manuel.mysportfolio.publishers;

import org.manuel.mysportfolio.model.entities.Competition;
import org.manuel.mysportfolio.model.events.CompetitionCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class CompetitionCreatedEventPublisher {

  private ApplicationEventPublisher applicationEventPublisher;

  public void publishEvent(final Competition createdCompetition) {
    log.info("Publishing competition created event for competition {}.",
        createdCompetition.getId().toString());
    final var competitionCreatedEvent = new CompetitionCreatedEvent(createdCompetition);
    applicationEventPublisher.publishEvent(competitionCreatedEvent);
  }

}
