package org.manuel.mysportfolio.model.dtos.team;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.validation.constraints.NotNull;

/**
 * Dto to be used if the home/away team is a registered team when user inputs a match
 */
@JsonDeserialize(builder = RegisteredTeamDto.RegisteredTeamDtoBuilder.class)
@JsonTypeName("registered")
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class RegisteredTeamDto implements TeamTypeDto {

    @NotNull
    private final String teamId;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class RegisteredTeamDtoBuilder {

    }
}
