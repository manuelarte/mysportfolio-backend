package org.manuel.mysportfolio.model.dtos.match;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.manuel.mysportfolio.model.dtos.PlayerDto;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;

import java.util.UUID;

@JsonDeserialize(builder = MatchDto.MatchDtoBuilder.class)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class MatchDto {

    private final UUID id;

    private final PlayerDto creator;

    private final TeamDto homeTeam;
    private final TeamDto awayTeam;

    // private final Set<EventDto> events;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class MatchDtoBuilder {

    }

}
