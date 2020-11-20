package org.manuel.mysportfolio.services.command.impl;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfileSportInfo;
import java.time.Year;
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
  public PlayerProfileSportInfo updateForYear(final String externalId, final Year year,
      final PlayerProfileSportInfo playerProfileSportInfo) {
    final var player = playerProfileQueryService.getByExternalId(externalId);
    player.getInfo().put(year, playerProfileSportInfo);
    playerProfileRepository.save(player);
    return playerProfileSportInfo;
  }

}
