package org.manuel.mysportfolio.model.entities.match;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.manuel.mysportfolio.model.dtos.team.AnonymousTeamDto;
import org.manuel.mysportfolio.model.entities.match.events.GoalMatchEvent;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = GoalMatchEvent.class, name = "goal"),
    @JsonSubTypes.Type(value = AnonymousTeamDto.class, name = "anonymous")})
public interface TeamType {

  String getType();
}
