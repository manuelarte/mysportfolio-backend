package org.manuel.mysportfolio.transformers.playerprofile;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfileFootballInfo;
import java.time.Month;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileFootballInfoDto;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileTimeIntervalSummaryDto;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerProfileFootballInfoToPlayerProfileFootballInfoDtoTransformer implements
    BiFunction<Map<Month, PlayerProfileTimeIntervalSummaryDto>, PlayerProfileFootballInfo, PlayerProfileFootballInfoDto> {

  @Override
  public PlayerProfileFootballInfoDto apply(
      final Map<Month, PlayerProfileTimeIntervalSummaryDto> summary,
      final PlayerProfileFootballInfo input) {
    return PlayerProfileFootballInfoDto.builder()
        .summary(summary)
        .preferredPosition(Optional.ofNullable(input).map(PlayerProfileFootballInfo::getPreferredPosition).orElse(null))
        .alternativePositions(Optional.ofNullable(input).map(PlayerProfileFootballInfo::getAlternativePositions).orElse(null))
        .skills(Optional.ofNullable(input).map(PlayerProfileFootballInfo::getSkills).orElse(null))
        .build();
  }
}
