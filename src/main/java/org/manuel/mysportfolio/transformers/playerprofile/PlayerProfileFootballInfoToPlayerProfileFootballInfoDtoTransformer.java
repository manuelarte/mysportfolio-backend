package org.manuel.mysportfolio.transformers.playerprofile;

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
        .preferredPosition(input.getPreferredPosition())
        .alternativePositions(input.getAlternativePositions())
        .skills(input.getSkills())
        .build();
  }
}
