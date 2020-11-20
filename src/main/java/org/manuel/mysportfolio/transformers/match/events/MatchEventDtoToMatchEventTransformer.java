package org.manuel.mysportfolio.transformers.match.events;

import io.github.manuelarte.mysportfolio.model.documents.match.events.MatchEvent;
import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.match.events.MatchEventDto;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class MatchEventDtoToMatchEventTransformer implements Function<MatchEventDto, MatchEvent> {

  @Override
  @lombok.SneakyThrows
  public MatchEvent apply(final MatchEventDto matchEventDto) {
    return matchEventDto != null ? matchEventDto.toMatchEvent() : null;
  }

}
