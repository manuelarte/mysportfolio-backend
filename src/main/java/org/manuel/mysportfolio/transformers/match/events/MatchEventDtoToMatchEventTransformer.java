package org.manuel.mysportfolio.transformers.match.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.manuel.mysportfolio.model.dtos.match.MatchEventDto;
import org.manuel.mysportfolio.model.entities.match.events.MatchEvent;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class MatchEventDtoToMatchEventTransformer implements Function<MatchEventDto, MatchEvent> {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public MatchEvent apply(final MatchEventDto matchEventDto) {
        return objectMapper.readValue(objectMapper.writeValueAsString(matchEventDto), MatchEvent.class);
    }

}
