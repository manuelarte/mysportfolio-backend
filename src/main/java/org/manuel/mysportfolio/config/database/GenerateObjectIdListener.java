package org.manuel.mysportfolio.config.database;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.events.MatchEvent;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class GenerateObjectIdListener extends AbstractMongoEventListener<Match> {

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Match> event) {
        final List<MatchEvent> matchEvents = Optional.ofNullable(event.getSource().getEvents()).orElse(Collections.emptyList());
        matchEvents.stream().filter(e -> e.getId() == null)
                .forEach(e -> e.setId(new ObjectId()));
    }

}
