package org.manuel.mysportfolio.transformers.playerprofile.sportinfo.football;

import io.github.manuelarte.mysportfolio.model.documents.player.sportinfo.PlayerFootballInfo;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileTimeIntervalSummaryDto;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.sportinfo.PlayerFootballInfoDto;
import java.time.Month;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerFootballInfoToPlayerFootballInfoDtoTransformer implements
    BiFunction<Map<Month, PlayerProfileTimeIntervalSummaryDto>, PlayerFootballInfo, PlayerFootballInfoDto> {

  @Override
  public PlayerFootballInfoDto apply(
      final Map<Month, PlayerProfileTimeIntervalSummaryDto> summary,
      final PlayerFootballInfo input) {
    return PlayerFootballInfoDto.builder()
        .summary(summary)
        .mainPosition(Optional.ofNullable(input).map(PlayerFootballInfo::getMainPosition).orElse(null))
        .alternativePositions(Optional.ofNullable(input).map(PlayerFootballInfo::getAlternativePositions).orElse(null))
        .skills(Optional.ofNullable(input).map(PlayerFootballInfo::getSkills).orElse(null))
        .build();
  }
}
