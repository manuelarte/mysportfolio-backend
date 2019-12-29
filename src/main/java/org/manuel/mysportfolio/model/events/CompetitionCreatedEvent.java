package org.manuel.mysportfolio.model.events;

import org.manuel.mysportfolio.model.entities.Competition;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.springframework.context.ApplicationEvent;

public class CompetitionCreatedEvent extends ApplicationEvent {

    /**
     * Create a new CompetitionCreatedEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public CompetitionCreatedEvent(final Competition source) {
        super(source);
    }
}
