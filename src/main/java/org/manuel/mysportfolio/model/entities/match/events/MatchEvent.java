package org.manuel.mysportfolio.model.entities.match.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.bson.types.ObjectId;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = DefaultMatchEvent.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GoalMatchEvent.class, name = "goal")})
public interface MatchEvent {

    ObjectId getId();

}
