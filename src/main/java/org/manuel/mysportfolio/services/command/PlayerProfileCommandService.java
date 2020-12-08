package org.manuel.mysportfolio.services.command;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfileSportInfo;
import java.time.Year;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

public interface PlayerProfileCommandService {

  @PreAuthorize("(hasRole('ROLE_ADMIN') or #externalId == authentication.principal.attributes['sub']) "
      + "and @permissionsService.canSavePlayerProfile(#externalId, #year)")
  List<PlayerProfileSportInfo<?>> updateForYear(String externalId, Year year, List<PlayerProfileSportInfo<?>> playerProfileSportsInfo);

}
