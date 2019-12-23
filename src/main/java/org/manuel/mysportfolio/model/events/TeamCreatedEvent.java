package org.manuel.mysportfolio.model.events;

import org.manuel.mysportfolio.model.entities.team.Team;
import org.springframework.context.ApplicationEvent;

public class TeamCreatedEvent extends ApplicationEvent {

    /**
     * Create a new TeamCreatedEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public TeamCreatedEvent(final Team source) {
        super(source);
    }
}
