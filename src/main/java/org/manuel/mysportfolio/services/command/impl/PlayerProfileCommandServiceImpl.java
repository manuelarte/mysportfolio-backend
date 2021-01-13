package org.manuel.mysportfolio.services.command.impl;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerSportInfo;
import io.github.manuelarte.mysportfolio.model.documents.player.PlayerSportsInfo;
import io.github.manuelarte.mysportfolio.model.documents.player.sportinfo.PlayerFootball7Info;
import io.github.manuelarte.mysportfolio.model.documents.player.sportinfo.PlayerFootballInfo;
import io.github.manuelarte.mysportfolio.model.documents.player.sportinfo.PlayerFutsalInfo;
import java.time.Year;
import java.util.Optional;
import org.manuel.mysportfolio.repositories.PlayerProfileRepository;
import org.manuel.mysportfolio.services.command.PlayerProfileCommandService;
import org.manuel.mysportfolio.services.query.PlayerProfileQueryService;
import org.springframework.stereotype.Service;

@Service
@lombok.RequiredArgsConstructor
public class PlayerProfileCommandServiceImpl implements PlayerProfileCommandService {

  private final PlayerProfileQueryService playerProfileQueryService;
  private final PlayerProfileRepository playerProfileRepository;

  @Override
  public PlayerSportsInfo updateForYear(final String externalId, final Year year,
      final PlayerSportsInfo profileSportsInfo) {
    final var player = playerProfileQueryService.getByExternalId(externalId);
    player.getInfo().put(year, profileSportsInfo);
    playerProfileRepository.save(player);
    return profileSportsInfo;
  }

  @Override
  public PlayerSportInfo<?> updateForYearAndSport(
      final String externalId,
      final Year year,
      final PlayerSportInfo<?> playerProfileSportInfo
  ) {
    final var player = playerProfileQueryService.getByExternalId(externalId);
    final var yearSportsInfo = Optional.ofNullable(player.getInfo()).map(it -> it.getOrDefault(year, new PlayerSportsInfo()))
        .orElse(new PlayerSportsInfo());

    final var builder = yearSportsInfo.toBuilder();
    if (playerProfileSportInfo instanceof PlayerFootballInfo) {
      builder.football((PlayerFootballInfo)playerProfileSportInfo);
    } else if (playerProfileSportInfo instanceof PlayerFootball7Info) {
      builder.football7((PlayerFootball7Info)playerProfileSportInfo);
    } else if (playerProfileSportInfo instanceof PlayerFutsalInfo) {
      builder.futsal((PlayerFutsalInfo)playerProfileSportInfo);
    }
    player.getInfo().put(year, builder.build());
    playerProfileRepository.save(player);
    return playerProfileSportInfo;
  }

}
