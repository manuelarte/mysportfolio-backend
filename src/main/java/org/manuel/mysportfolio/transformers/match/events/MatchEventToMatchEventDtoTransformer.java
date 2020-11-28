package org.manuel.mysportfolio.transformers.match.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.manuelarte.mysportfolio.model.documents.match.events.DefaultMatchEvent;
import io.github.manuelarte.mysportfolio.model.documents.match.events.GoalMatchEvent;
import io.github.manuelarte.mysportfolio.model.documents.match.events.HalfTimeMatchEvent;
import io.github.manuelarte.mysportfolio.model.documents.match.events.MatchEvent;
import io.github.manuelarte.mysportfolio.model.documents.match.events.SubstitutionMatchEvent;
import java.util.Optional;
import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.match.events.AssistDetailsDto;
import org.manuel.mysportfolio.model.dtos.match.events.DefaultMatchEventDto;
import org.manuel.mysportfolio.model.dtos.match.events.GoalMatchEventDto;
import org.manuel.mysportfolio.model.dtos.match.events.HalfTimeMatchEventDto;
import org.manuel.mysportfolio.model.dtos.match.events.MatchEventDto;
import org.manuel.mysportfolio.model.dtos.match.events.SubstitutionMatchEventDto;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class MatchEventToMatchEventDtoTransformer implements Function<MatchEvent, MatchEventDto<MatchEvent>> {

  private final ObjectMapper objectMapper;

  @Override
  @lombok.SneakyThrows
  public MatchEventDto<MatchEvent> apply(final MatchEvent matchEvent) {
    final MatchEventDto<? extends MatchEvent> toReturn;
    if (matchEvent instanceof GoalMatchEvent) {
      final var casted = (GoalMatchEvent) matchEvent;
      toReturn = GoalMatchEventDto.builder()
          .id(casted.getId())
          .team(casted.getTeam())
          .playerId(casted.getPlayerId())
          .minute(casted.getMinute())
          .goalCoordinates(casted.getGoalCoordinates())
          .bodyPart(casted.getBodyPart())
          .rates(casted.getRates())
          .description(casted.getDescription())
          .assist(Optional.ofNullable(casted.getAssist()).map(
              it -> AssistDetailsDto.builder()
                  .who(it.getWho())
                  .bodyPart(it.getBodyPart())
                  .build()).orElse(null)
          )
          .build();
    } else if (matchEvent instanceof SubstitutionMatchEvent) {
      final var casted = (SubstitutionMatchEvent) matchEvent;
      toReturn = SubstitutionMatchEventDto.builder()
          .id(casted.getId())
          .minute(casted.getMinute())
          .team(casted.getTeam())
          .in(casted.getIn())
          .out(casted.getOut())
          .description(casted.getDescription())
          .build();
    } else if (matchEvent instanceof HalfTimeMatchEvent) {
      final var casted = (HalfTimeMatchEvent) matchEvent;
      toReturn = HalfTimeMatchEventDto.builder()
          .id(casted.getId())
          .duration(casted.getDuration())
          .build();
    } else if (matchEvent instanceof DefaultMatchEvent) {
      final var casted = (DefaultMatchEvent) matchEvent;
      final var defaultDto = new DefaultMatchEventDto();
      defaultDto.setId(casted.getId());
      defaultDto.setMap(casted.getMap());
      toReturn = defaultDto;
    } else {
      toReturn = null;
    }
    return (MatchEventDto<MatchEvent>) toReturn;
  }

}
