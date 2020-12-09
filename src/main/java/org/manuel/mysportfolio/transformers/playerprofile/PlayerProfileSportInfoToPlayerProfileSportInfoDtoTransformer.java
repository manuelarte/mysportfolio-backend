package org.manuel.mysportfolio.transformers.playerprofile;

import io.github.manuelarte.mysportfolio.model.Sport;
import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfileFootball7Info;
import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfileFootballInfo;
import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfileFutsalInfo;
import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfileSportInfo;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileSportInfoDto;
import io.jsonwebtoken.lang.Assert;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.manuel.mysportfolio.services.query.MatchQueryService;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerProfileSportInfoToPlayerProfileSportInfoDtoTransformer {

  private final MatchQueryService matchQueryService;
  private final PlayerProfileFootballInfoToPlayerProfileFootballInfoDtoTransformer
      playerProfileFootballInfoToPlayerProfileFootballInfoDtoTransformer;
  private final PlayerProfileFootball7InfoToPlayerProfileFootball7InfoDtoTransformer
      playerProfileFootball7InfoToPlayerProfileFootball7InfoDtoTransformer;
  private final PlayerProfileFutsalInfoToPlayerProfileFutsalInfoDtoTransformer
      playerProfileFutsalInfoToPlayerProfileFutsalInfoDtoTransformer;

  public List<PlayerProfileSportInfoDto<?>> apply(
      @NotNull final String externalId,
      @NotNull final Year year,
      @NotNull final List<PlayerProfileSportInfo<?>> playerProfileSportsInfo) {
    Assert.notNull(externalId, "The external id can't be null");
    Assert.notNull(year, "The year can't be null");
    Assert.notNull(playerProfileSportsInfo, "The list of sports info can't be null");
    final var summaryTransformer = new PlayerProfileSportSeasonSummaryDtoTransformer(externalId,
        year, matchQueryService);
    final var footballSummary = summaryTransformer.apply(Sport.FOOTBALL);
    final var football7Summary = summaryTransformer.apply(Sport.FOOTBALL_7);
    final var futsalSummary = summaryTransformer.apply(Sport.FUTSAL);

    final var footballInfo =
        getPlayerProfileSportInfo(PlayerProfileFootballInfo.class, playerProfileSportsInfo);
    final var footballInfoDto = playerProfileFootballInfoToPlayerProfileFootballInfoDtoTransformer
        .apply(footballSummary, footballInfo.orElse(null));
    final var football7Info = getPlayerProfileSportInfo(PlayerProfileFootball7Info.class, playerProfileSportsInfo);
    final var football7InfoDto = playerProfileFootball7InfoToPlayerProfileFootball7InfoDtoTransformer
        .apply(football7Summary, football7Info.orElse(null));
    final var futsalInfo = getPlayerProfileSportInfo(PlayerProfileFutsalInfo.class, playerProfileSportsInfo);
    final var futsalInfoDto = playerProfileFutsalInfoToPlayerProfileFutsalInfoDtoTransformer
        .apply(futsalSummary, futsalInfo.orElse(null));

    return List.of(footballInfoDto, football7InfoDto, futsalInfoDto);
  }

  private <T extends PlayerProfileSportInfo<?>> Optional<T> getPlayerProfileSportInfo(
      final Class<T> clazz, final List<PlayerProfileSportInfo<?>> playerProfileSportsInfo) {
    assert playerProfileSportsInfo != null;
    return playerProfileSportsInfo.stream()
        .filter(it -> it.getClass().equals(clazz))
        .map(clazz::cast).findAny();

  }

}
