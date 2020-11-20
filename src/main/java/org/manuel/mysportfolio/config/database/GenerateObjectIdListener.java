package org.manuel.mysportfolio.config.database;

import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import io.github.manuelarte.mysportfolio.model.documents.match.events.MatchEvent;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class GenerateObjectIdListener extends
    AbstractMongoEventListener<Match<TeamType, TeamType>> {

  @Override
  public void onBeforeConvert(final BeforeConvertEvent<Match<TeamType, TeamType>> event) {
    final List<MatchEvent> matchEvents = Optional.ofNullable(event.getSource().getEvents())
        .orElse(Collections.emptyList());
    matchEvents.stream().filter(e -> e.getId() == null)
        .forEach(e -> e.setId(new ObjectId()));
  }

}
