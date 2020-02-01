package org.manuel.mysportfolio.model.events;

import org.manuel.mysportfolio.model.entities.Competition;

public class CompetitionCreatedEvent extends SportEvent<Competition> {

    /**
     * Create a new CompetitionCreatedEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public CompetitionCreatedEvent(final Competition source) {
        super(source);
    }
}
