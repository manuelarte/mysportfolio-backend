package org.manuel.mysportfolio.transformers.playerprofile;

import io.github.manuelarte.mysportfolio.model.Sport;
import io.github.manuelarte.mysportfolio.model.documents.player.PlayerSportsInfo;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerSportsInfoDto;
import io.jsonwebtoken.lang.Assert;
import java.time.Year;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.manuel.mysportfolio.services.query.MatchQueryService;
import org.manuel.mysportfolio.transformers.playerprofile.sportinfo.football.PlayerFootballInfoToPlayerFootballInfoDtoTransformer;
import org.manuel.mysportfolio.transformers.playerprofile.sportinfo.football7.PlayerFootball7InfoToPlayerFootball7InfoDtoTransformer;
import org.manuel.mysportfolio.transformers.playerprofile.sportinfo.futsal.PlayerFutsalInfoToPlayerFutsalInfoDtoTransformer;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerSportsInfoToPlayerSportsInfoDtoTransformer {

  private final MatchQueryService matchQueryService;
  private final PlayerFootballInfoToPlayerFootballInfoDtoTransformer
      playerFootballInfoToPlayerFootballInfoDtoTransformer;
  private final PlayerFootball7InfoToPlayerFootball7InfoDtoTransformer
      playerFootball7InfoToPlayerFootball7InfoDtoTransformer;
  private final PlayerFutsalInfoToPlayerFutsalInfoDtoTransformer
      playerFutsalInfoToPlayerFutsalInfoDtoTransformer;

  public PlayerSportsInfoDto apply(
      @NotNull final String externalId,
      @NotNull final Year year,
      @NotNull final PlayerSportsInfo playerSportsInfo) {
    Assert.notNull(externalId, "The external id can't be null");
    Assert.notNull(year, "The year can't be null");
    Assert.notNull(playerSportsInfo, "The sports info can't be null");
    final var summaryTransformer = new PlayerProfileSportSeasonSummaryDtoTransformer(externalId,
        year, matchQueryService);
    final var footballSummary = summaryTransformer.apply(Sport.FOOTBALL);
    final var football7Summary = summaryTransformer.apply(Sport.FOOTBALL_7);
    final var futsalSummary = summaryTransformer.apply(Sport.FUTSAL);

    final var footballInfo = Optional.ofNullable(playerSportsInfo.getFootball());
    final var footballInfoDto = playerFootballInfoToPlayerFootballInfoDtoTransformer
        .apply(footballSummary, footballInfo.orElse(null));
    final var football7Info = Optional.ofNullable(playerSportsInfo.getFootball7());
    final var football7InfoDto = playerFootball7InfoToPlayerFootball7InfoDtoTransformer
        .apply(football7Summary, football7Info.orElse(null));
    final var futsalInfo = Optional.ofNullable(playerSportsInfo.getFutsal());
    final var futsalInfoDto = playerFutsalInfoToPlayerFutsalInfoDtoTransformer
        .apply(futsalSummary, futsalInfo.orElse(null));

    return new PlayerSportsInfoDto(footballInfoDto, football7InfoDto, futsalInfoDto);
  }

}
