package org.manuel.mysportfolio.transformers.match.events;

import io.github.manuelarte.mysportfolio.model.documents.match.events.AssistDetails;
import io.github.manuelarte.mysportfolio.model.documents.match.events.DefaultMatchEvent;
import io.github.manuelarte.mysportfolio.model.documents.match.events.GoalMatchEvent;
import io.github.manuelarte.mysportfolio.model.documents.match.events.HalfTimeMatchEvent;
import io.github.manuelarte.mysportfolio.model.documents.match.events.MatchEvent;
import io.github.manuelarte.mysportfolio.model.documents.match.events.SubstitutionMatchEvent;
import io.github.manuelarte.mysportfolio.model.dtos.match.events.AssistDetailsDto;
import io.github.manuelarte.mysportfolio.model.dtos.match.events.DefaultMatchEventDto;
import io.github.manuelarte.mysportfolio.model.dtos.match.events.GoalMatchEventDto;
import io.github.manuelarte.mysportfolio.model.dtos.match.events.HalfTimeMatchEventDto;
import io.github.manuelarte.mysportfolio.model.dtos.match.events.MatchEventDto;
import io.github.manuelarte.mysportfolio.model.dtos.match.events.SubstitutionMatchEventDto;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class MatchEventDtoToMatchEventTransformer implements Function<MatchEventDto<?>, MatchEvent> {

  private final GoalCoordinatesDtoToGoalCoordinates goalCoordinatesDtoToGoalCoordinates;

  @Override
  @lombok.SneakyThrows
  public MatchEvent apply(final MatchEventDto<?> matchEventDto) {
    return matchEventDto != null ? toMatchEvent(matchEventDto) : null;
  }

  private MatchEvent toMatchEvent(final MatchEventDto<?> matchEventDto) {
    if (matchEventDto instanceof GoalMatchEventDto) {
      final var goalMatchEventDto = (GoalMatchEventDto) matchEventDto;
      return new GoalMatchEvent(goalMatchEventDto.getId(), goalMatchEventDto.getTeam(), goalMatchEventDto.getPlayerId(),
          goalMatchEventDto.getMinute(), goalCoordinatesDtoToGoalCoordinates.apply(goalMatchEventDto.getGoalCoordinates()),
          goalMatchEventDto.getBodyPart(), goalMatchEventDto.getRates(), goalMatchEventDto.getDescription(),
          Optional.ofNullable(goalMatchEventDto.getAssist()).map(this::toAssistDetails).orElse(null));
    } else if (matchEventDto instanceof HalfTimeMatchEventDto) {
      final var halfTimeMatchEventDto = (HalfTimeMatchEventDto) matchEventDto;
      return new HalfTimeMatchEvent(halfTimeMatchEventDto.getId(), halfTimeMatchEventDto.getMinute());
    } else if (matchEventDto instanceof SubstitutionMatchEventDto) {
      final var substitutionMatchEventDto = (SubstitutionMatchEventDto) matchEventDto;
      return new SubstitutionMatchEvent(substitutionMatchEventDto.getId(), substitutionMatchEventDto.getTeam(),
          substitutionMatchEventDto.getMinute(), substitutionMatchEventDto.getIn(), substitutionMatchEventDto.getOut(),
          substitutionMatchEventDto.getDescription());
    } else if (matchEventDto instanceof DefaultMatchEventDto) {
      final var defaultMatchEventDto = (DefaultMatchEventDto) matchEventDto;
      final var defaultMatchEvent = new DefaultMatchEvent();
      defaultMatchEvent.setId(defaultMatchEventDto.getId());
      defaultMatchEvent.setMinute(defaultMatchEventDto.getMinute());
      defaultMatchEvent.setMap(defaultMatchEventDto.getMap());
      return defaultMatchEvent;
    } else {
      return null;
    }

  }

  private AssistDetails toAssistDetails(final AssistDetailsDto assistDetailsDto) {
    return new AssistDetails(assistDetailsDto.getWho(), assistDetailsDto.getBodyPart());
  }

}
