package org.manuel.mysportfolio.model.events;

import org.manuel.mysportfolio.model.entities.team.Team;

public class TeamCreatedEvent extends SportEvent<Team> {

    /**
     * Create a new TeamCreatedEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public TeamCreatedEvent(final Team source) {
        super(source);
    }
}
