package org.manuel.mysportfolio.services.command;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerSportInfo;
import io.github.manuelarte.mysportfolio.model.documents.player.PlayerSportsInfo;
import java.time.Year;
import org.springframework.security.access.prepost.PreAuthorize;

public interface PlayerProfileCommandService {

  @PreAuthorize("(hasRole('ROLE_ADMIN') or #externalId == authentication.principal.attributes['sub']) "
      + "and @permissionsService.canSavePlayerProfile(#externalId, #year)")
  PlayerSportsInfo updateForYear(String externalId, Year year, PlayerSportsInfo playerProfileSportsInfo);


  @PreAuthorize("(hasRole('ROLE_ADMIN') or #externalId == authentication.principal.attributes['sub']) "
      + "and @permissionsService.canSavePlayerProfile(#externalId, #year)")
  @SuppressWarnings("UnusedReturnValue")
  PlayerSportInfo<?> updateForYearAndSport(String externalId, Year year, PlayerSportInfo<?> playerProfileSportInfo);

}
