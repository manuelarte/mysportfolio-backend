package org.manuel.mysportfolio.services.query;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfile;

public interface PlayerProfileQueryService {

  PlayerProfile getByExternalId(String externalId);

}
