package org.manuel.mysportfolio.model.dtos.team;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.manuel.mysportfolio.model.dtos.match.MatchDto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.net.URL;

@JsonDeserialize(builder = MatchDto.MatchDtoBuilder.class)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class TeamDto {

    private final String id;

    @NotEmpty
    @Max(30)
    private final String name;

    @org.hibernate.validator.constraints.URL
    private final URL imageLink;
}
