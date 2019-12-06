package org.manuel.mysportfolio.publishers;

import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.model.events.MatchCreatedEvent;
import org.manuel.mysportfolio.model.events.TeamCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class MatchCreatedEventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(final Match createdMatch) {
        log.info("Publishing match created event for match {}.", createdMatch.getId().toString());
        final MatchCreatedEvent teamCreatedEvent = new MatchCreatedEvent(createdMatch);
        applicationEventPublisher.publishEvent(teamCreatedEvent);
    }

}
