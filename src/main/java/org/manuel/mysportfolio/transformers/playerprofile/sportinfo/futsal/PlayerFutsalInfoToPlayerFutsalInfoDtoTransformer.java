package org.manuel.mysportfolio.transformers.playerprofile.sportinfo.futsal;

import io.github.manuelarte.mysportfolio.model.documents.player.sportinfo.PlayerFutsalInfo;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileTimeIntervalSummaryDto;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.sportinfo.PlayerFutsalInfoDto;
import java.time.Month;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerFutsalInfoToPlayerFutsalInfoDtoTransformer implements
    BiFunction<Map<Month, PlayerProfileTimeIntervalSummaryDto>, PlayerFutsalInfo, PlayerFutsalInfoDto> {

  @Override
  public PlayerFutsalInfoDto apply(final Map<Month, PlayerProfileTimeIntervalSummaryDto> summary,
      final PlayerFutsalInfo input) {
    return PlayerFutsalInfoDto.builder()
        .summary(summary)
        .mainPosition(Optional.ofNullable(input).map(PlayerFutsalInfo::getMainPosition).orElse(null))
        .alternativePositions(Optional.ofNullable(input).map(PlayerFutsalInfo::getAlternativePositions).orElse(null))
        .skills(Optional.ofNullable(input).map(PlayerFutsalInfo::getSkills).orElse(null))
        .build();
  }
}
