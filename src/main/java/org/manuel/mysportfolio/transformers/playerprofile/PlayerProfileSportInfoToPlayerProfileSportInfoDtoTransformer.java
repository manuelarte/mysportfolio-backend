package org.manuel.mysportfolio.transformers.playerprofile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileSportInfoDto;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileSportSeasonSummaryDto;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.events.AssistDetails;
import org.manuel.mysportfolio.model.entities.match.events.GoalMatchEvent;
import org.manuel.mysportfolio.model.entities.player.PlayerProfileSportInfo;
import org.manuel.mysportfolio.services.query.MatchQueryService;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerProfileSportInfoToPlayerProfileSportInfoDtoTransformer {

  private final MatchQueryService matchQueryService;
  private final PlayerProfileFootballInfoToPlayerProfileFootballInfoDtoTransformer playerProfileFootballInfoToPlayerProfileFootballInfoDtoTransformer;
  private final PlayerProfileFutsalInfoToPlayerProfileFutsalInfoDtoTransformer playerProfileFutsalInfoToPlayerProfileFutsalInfoDtoTransformer;

  public PlayerProfileSportInfoDto apply(final String externalId, final Year year, final PlayerProfileSportInfo playerProfileSportInfo) {
    final var summaryTransformer = new PlayerProfileSportSeasonSummaryDtoTransformer(externalId, year, matchQueryService);
    final var footballSummary = summaryTransformer.apply(Sport.FOOTBALL);
    final var futsalSummary = summaryTransformer.apply(Sport.FUTSAL);
    return PlayerProfileSportInfoDto.builder()
        .footballInfo(playerProfileFootballInfoToPlayerProfileFootballInfoDtoTransformer.apply(footballSummary, playerProfileSportInfo.getFootballInfo()))
        .futsalInfo(playerProfileFutsalInfoToPlayerProfileFutsalInfoDtoTransformer.apply(futsalSummary, playerProfileSportInfo.getFutsalInfo()))
        .build();
  }

}
