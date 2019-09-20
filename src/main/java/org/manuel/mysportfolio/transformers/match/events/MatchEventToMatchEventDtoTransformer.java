package org.manuel.mysportfolio.transformers.match.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.manuel.mysportfolio.model.dtos.match.MatchEventDto;
import org.manuel.mysportfolio.model.entities.match.events.MatchEvent;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class MatchEventToMatchEventDtoTransformer implements Function<MatchEvent, MatchEventDto> {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public MatchEventDto apply(final MatchEvent matchEvent) {
        return objectMapper.readValue(objectMapper.writeValueAsString(matchEvent), MatchEventDto.class);
    }

}
