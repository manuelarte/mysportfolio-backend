package org.manuel.mysportfolio.publishers;

import org.manuel.mysportfolio.model.entities.match.PlayersPerformance;
import org.manuel.mysportfolio.model.events.PlayersPerformanceUpdatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class PlayersPerformanceUpdatedEventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(final PlayersPerformance playersPerformance) {
        log.info("Publishing playersPerformance updated event for match {}.", playersPerformance.getMatchId());
        final var teamCreatedEvent = new PlayersPerformanceUpdatedEvent(playersPerformance);
        applicationEventPublisher.publishEvent(teamCreatedEvent);
    }

}
