package org.manuel.mysportfolio.transformers.playerprofile.sportinfo.football7;

import io.github.manuelarte.mysportfolio.model.documents.player.sportinfo.PlayerFootball7Info;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileTimeIntervalSummaryDto;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.sportinfo.PlayerFootball7InfoDto;
import java.time.Month;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerFootball7InfoToPlayerFootball7InfoDtoTransformer implements
    BiFunction<Map<Month, PlayerProfileTimeIntervalSummaryDto>, PlayerFootball7Info, PlayerFootball7InfoDto> {

  @Override
  public PlayerFootball7InfoDto apply(
      final Map<Month, PlayerProfileTimeIntervalSummaryDto> summary,
      final PlayerFootball7Info input) {
    return PlayerFootball7InfoDto.builder()
        .summary(summary)
        .mainPosition(Optional.ofNullable(input).map(PlayerFootball7Info::getMainPosition).orElse(null))
        .alternativePositions(Optional.ofNullable(input).map(PlayerFootball7Info::getAlternativePositions).orElse(null))
        .skills(Optional.ofNullable(input).map(PlayerFootball7Info::getSkills).orElse(null))
        .build();
  }
}
