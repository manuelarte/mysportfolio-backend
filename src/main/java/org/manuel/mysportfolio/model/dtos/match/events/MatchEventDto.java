package org.manuel.mysportfolio.model.dtos.match.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.manuelarte.mysportfolio.model.documents.match.events.MatchEvent;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = DefaultMatchEventDto.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = GoalMatchEventDto.class, name = "goal"),
    @JsonSubTypes.Type(value = SubstitutionMatchEventDto.class, name = "substitution"),
    @JsonSubTypes.Type(value = HalfTimeMatchEventDto.class, name = "half-time"),
})
public interface MatchEventDto<T extends MatchEvent> {

  String getId();

  T toMatchEvent();

}
