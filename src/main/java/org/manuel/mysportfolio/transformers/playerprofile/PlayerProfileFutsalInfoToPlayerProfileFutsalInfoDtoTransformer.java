package org.manuel.mysportfolio.transformers.playerprofile;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfileFutsalInfo;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileFutsalInfoDto;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileTimeIntervalSummaryDto;
import java.time.Month;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerProfileFutsalInfoToPlayerProfileFutsalInfoDtoTransformer implements
    BiFunction<Map<Month, PlayerProfileTimeIntervalSummaryDto>, PlayerProfileFutsalInfo, PlayerProfileFutsalInfoDto> {

  @Override
  public PlayerProfileFutsalInfoDto apply(final Map<Month, PlayerProfileTimeIntervalSummaryDto> summary,
      final PlayerProfileFutsalInfo input) {
    return PlayerProfileFutsalInfoDto.builder()
        .summary(summary)
        .preferredPosition(Optional.ofNullable(input).map(PlayerProfileFutsalInfo::getPreferredPosition).orElse(null))
        .alternativePositions(Optional.ofNullable(input).map(PlayerProfileFutsalInfo::getAlternativePositions).orElse(null))
        .skills(Optional.ofNullable(input).map(PlayerProfileFutsalInfo::getSkills).orElse(null))
        .build();
  }
}
