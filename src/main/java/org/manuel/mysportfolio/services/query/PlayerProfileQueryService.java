package org.manuel.mysportfolio.services.query;

import org.manuel.mysportfolio.model.entities.player.PlayerProfile;

public interface PlayerProfileQueryService {

  PlayerProfile getByExternalId(String externalId);

}
