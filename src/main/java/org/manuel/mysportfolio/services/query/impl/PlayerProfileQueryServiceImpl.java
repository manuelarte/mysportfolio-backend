package org.manuel.mysportfolio.services.query.impl;

import io.jsonwebtoken.lang.Assert;
import javax.validation.constraints.NotNull;
import org.manuel.mysportfolio.model.entities.player.PlayerProfile;
import org.manuel.mysportfolio.repositories.AppUserRepository;
import org.manuel.mysportfolio.repositories.PlayerProfileRepository;
import org.manuel.mysportfolio.services.query.PlayerProfileQueryService;
import org.springframework.stereotype.Service;

@Service
@lombok.AllArgsConstructor
public class PlayerProfileQueryServiceImpl implements PlayerProfileQueryService {

  private final AppUserRepository appUserRepository;
  private final PlayerProfileRepository playerProfileRepository;

  @Override
  public PlayerProfile getByExternalId(@NotNull final String externalId) {
    Assert.notNull(externalId, "The external id can't be null");
    Assert.isTrue(appUserRepository.existsByExternalId(externalId),
        String.format("The user with external id %s does not exists", externalId));
    return playerProfileRepository
        .findByExternalIdIs(externalId)
        .orElseGet(() -> playerProfileRepository.save(new PlayerProfile(externalId)));
  }
}
