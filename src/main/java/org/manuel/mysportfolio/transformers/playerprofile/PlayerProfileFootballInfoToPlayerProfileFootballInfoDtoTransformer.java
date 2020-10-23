package org.manuel.mysportfolio.transformers.playerprofile;

import java.util.Optional;
import java.util.function.BiFunction;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileFootballInfoDto;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileSportSeasonSummaryDto;
import org.manuel.mysportfolio.model.entities.player.PlayerProfileFootballInfo;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerProfileFootballInfoToPlayerProfileFootballInfoDtoTransformer implements
    BiFunction<PlayerProfileSportSeasonSummaryDto, PlayerProfileFootballInfo, PlayerProfileFootballInfoDto> {

  @Override
  public PlayerProfileFootballInfoDto apply(final PlayerProfileSportSeasonSummaryDto summary,
      final PlayerProfileFootballInfo input) {
    return PlayerProfileFootballInfoDto.builder()
        .summary(summary)
        .preferredPosition(Optional.ofNullable(input).map(PlayerProfileFootballInfo::getPreferredPosition).orElse(null))
        .alternativePositions(Optional.ofNullable(input).map(PlayerProfileFootballInfo::getAlternativePositions).orElse(null))
        .skills(Optional.ofNullable(input).map(PlayerProfileFootballInfo::getSkills).orElse(null))
        .build();
  }
}
