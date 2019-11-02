package org.manuel.mysportfolio.model.dtos.team;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RegisteredTeamDto.class, name = "registered"),
        @JsonSubTypes.Type(value = AnonymousTeamDto.class, name = "anonymous")})
public interface TeamTypeDto {

}
