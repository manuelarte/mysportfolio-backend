package org.manuel.mysportfolio.model.dtos.team;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.manuel.mysportfolio.model.entities.team.KitPart;
import org.manuel.mysportfolio.model.entities.team.TeamImage;
import org.manuel.mysportfolio.model.entities.team.TeamKit;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Dto to be used if the home/away team is an anonymous team when user inputs a match
 */
@JsonDeserialize(builder = AnonymousTeamDto.AnonymousTeamDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeName("anonymous")
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class AnonymousTeamDto implements TeamTypeDto {

    @NotEmpty
    @Size(max = 30)
    private final String name;

    private final TeamKit<KitPart, KitPart> teamKit;

    private final TeamImage teamImage;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class AnonymousTeamDtoBuilder {

    }
}
