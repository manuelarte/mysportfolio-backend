package org.manuel.mysportfolio.model.entities.match.type;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = DefaultMatchType.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = CompetitionMatchType.class, name = "competition"),
    @JsonSubTypes.Type(value = FriendlyMatchType.class, name = "friendly")
})
public interface MatchType {

}
