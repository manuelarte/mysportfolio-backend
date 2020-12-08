package org.manuel.mysportfolio.transformers.playerprofile;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfileFootball7Info;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileFootball7InfoDto;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileTimeIntervalSummaryDto;
import java.time.Month;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerProfileFootball7InfoToPlayerProfileFootball7InfoDtoTransformer implements
    BiFunction<Map<Month, PlayerProfileTimeIntervalSummaryDto>, PlayerProfileFootball7Info, PlayerProfileFootball7InfoDto> {

  @Override
  public PlayerProfileFootball7InfoDto apply(
      final Map<Month, PlayerProfileTimeIntervalSummaryDto> summary,
      final PlayerProfileFootball7Info input) {
    return PlayerProfileFootball7InfoDto.builder()
        .summary(summary)
        .preferredPosition(Optional.ofNullable(input).map(PlayerProfileFootball7Info::getPreferredPosition).orElse(null))
        .alternativePositions(Optional.ofNullable(input).map(PlayerProfileFootball7Info::getAlternativePositions).orElse(null))
        .skills(Optional.ofNullable(input).map(PlayerProfileFootball7Info::getSkills).orElse(null))
        .build();
  }
}
