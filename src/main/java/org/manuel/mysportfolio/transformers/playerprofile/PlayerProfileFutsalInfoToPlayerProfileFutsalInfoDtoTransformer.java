package org.manuel.mysportfolio.transformers.playerprofile;

import java.util.function.BiFunction;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileFutsalInfoDto;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileSportSeasonSummaryDto;
import org.manuel.mysportfolio.model.entities.player.PlayerProfileFutsalInfo;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerProfileFutsalInfoToPlayerProfileFutsalInfoDtoTransformer implements
    BiFunction<PlayerProfileSportSeasonSummaryDto, PlayerProfileFutsalInfo, PlayerProfileFutsalInfoDto> {

  @Override
  public PlayerProfileFutsalInfoDto apply(final PlayerProfileSportSeasonSummaryDto summary,
      final PlayerProfileFutsalInfo input) {
    return PlayerProfileFutsalInfoDto.builder()
        .summary(summary)
        .preferredPosition(input.getPreferredPosition())
        .alternativePositions(input.getAlternativePositions())
        .skills(input.getSkills())
        .build();
  }
}
