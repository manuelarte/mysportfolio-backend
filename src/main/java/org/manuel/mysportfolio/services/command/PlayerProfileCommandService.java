package org.manuel.mysportfolio.services.command;

import java.time.Year;
import org.manuel.mysportfolio.model.entities.player.PlayerProfileSportInfo;
import org.springframework.security.access.prepost.PreAuthorize;

public interface PlayerProfileCommandService {

  @PreAuthorize("(hasRole('ROLE_ADMIN') or #externalId == authentication.principal.attributes['sub']) "
      + "and @permissionsService.canSavePlayerProfile(#externalId, #year)")
  PlayerProfileSportInfo updateForYear(String externalId, Year year, PlayerProfileSportInfo playerProfileSportInfo);

}
