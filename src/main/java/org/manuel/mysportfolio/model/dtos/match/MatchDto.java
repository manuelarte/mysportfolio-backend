package org.manuel.mysportfolio.model.dtos.match;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.manuel.mysportfolio.model.dtos.team.TeamInMatchDto;

@JsonDeserialize(builder = MatchDto.MatchDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class MatchDto<HT extends TeamInMatchDto, AT extends TeamInMatchDto> {

    private final String id;

    private final Sport sport;

    private final SportType type;

    private final HT homeTeam;
    private final AT awayTeam;

    // private final Set<EventDto> events;

    private final String createdBy;

    public static <HT extends TeamInMatchDto, AT extends TeamInMatchDto> MatchDtoBuilder builder() {
        return new MatchDtoBuilder<HT, AT>();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class MatchDtoBuilder<HT extends TeamInMatchDto, AT extends TeamInMatchDto> {

    }

}
