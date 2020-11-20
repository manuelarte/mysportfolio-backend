package org.manuel.mysportfolio.model.dtos.match;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.manuelarte.mysportfolio.model.documents.match.type.DefaultMatchType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = DefaultMatchType.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = CompetitionMatchTypeDto.class, name = "competition"),
    @JsonSubTypes.Type(value = FriendlyMatchTypeDto.class, name = "friendly")
})
public interface MatchTypeDto {

}
