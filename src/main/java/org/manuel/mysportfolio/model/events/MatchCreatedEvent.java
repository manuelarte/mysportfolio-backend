package org.manuel.mysportfolio.model.events;

import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.springframework.context.ApplicationEvent;

public class MatchCreatedEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MatchCreatedEvent(final Match source) {
        super(source);
    }
}
