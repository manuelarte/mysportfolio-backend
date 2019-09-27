package org.manuel.mysportfolio.model.dtos.team;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.manuel.mysportfolio.model.TeamInfo;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.net.URL;

/**
 * Dto to use when the user is registering a new team
 * or
 * when the team info is displayed
 */
@JsonDeserialize(builder = TeamDto.TeamDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class TeamDto implements TeamInfo {

    private final String id;

    @NotEmpty
    @Size(max = 30)
    private final String name;

    @org.hibernate.validator.constraints.URL
    private final String imageLink;

    private final String createdBy;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class TeamDtoBuilder {

    }
}
