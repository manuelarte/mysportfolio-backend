package org.manuel.mysportfolio.publishers;

import io.github.manuelarte.mysportfolio.model.documents.team.Team;
import org.manuel.mysportfolio.model.events.TeamCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class TeamCreatedEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publishEvent(final Team createdTeam) {
    assert createdTeam.getId() != null;
    log.info("Publishing team created event for team {}.", createdTeam.getId().toString());
    final var teamCreatedEvent = new TeamCreatedEvent(createdTeam);
    applicationEventPublisher.publishEvent(teamCreatedEvent);
  }

}
