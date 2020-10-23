package org.manuel.mysportfolio.transformers.playerprofile;

import java.time.Year;
import java.util.Optional;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileSportInfoDto;
import org.manuel.mysportfolio.model.entities.player.PlayerProfileSportInfo;
import org.manuel.mysportfolio.services.query.MatchQueryService;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerProfileSportInfoToPlayerProfileSportInfoDtoTransformer {

  private final MatchQueryService matchQueryService;
  private final PlayerProfileFootballInfoToPlayerProfileFootballInfoDtoTransformer playerProfileFootballInfoToPlayerProfileFootballInfoDtoTransformer;
  private final PlayerProfileFutsalInfoToPlayerProfileFutsalInfoDtoTransformer playerProfileFutsalInfoToPlayerProfileFutsalInfoDtoTransformer;

  public PlayerProfileSportInfoDto apply(final String externalId, final Year year,
      final PlayerProfileSportInfo playerProfileSportInfo) {
    final var summaryTransformer = new PlayerProfileSportSeasonSummaryDtoTransformer(externalId,
        year, matchQueryService);
    final var footballSummary = summaryTransformer.apply(Sport.FOOTBALL);
    final var futsalSummary = summaryTransformer.apply(Sport.FUTSAL);
    return PlayerProfileSportInfoDto.builder()
        .footballInfo(playerProfileFootballInfoToPlayerProfileFootballInfoDtoTransformer
            .apply(footballSummary,
                Optional.ofNullable(playerProfileSportInfo).map(PlayerProfileSportInfo::getFootballInfo).orElse(null)))
        .futsalInfo(playerProfileFutsalInfoToPlayerProfileFutsalInfoDtoTransformer
            .apply(futsalSummary, Optional.ofNullable(playerProfileSportInfo).map(PlayerProfileSportInfo::getFutsalInfo).orElse(null)))
        .build();
  }

}
