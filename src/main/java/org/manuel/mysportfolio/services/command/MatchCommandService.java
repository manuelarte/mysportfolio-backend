package org.manuel.mysportfolio.services.command;

import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public interface MatchCommandService {

  @PreAuthorize("hasRole('ROLE_USER') and @permissionsService.canSaveMatch()")
  Match<TeamType, TeamType> save(Match<TeamType, TeamType> match);

  // TODO Post authorize isn't working. It commits the change even that 403 is returned
  @PostAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') and returnObject.createdBy.get() == authentication.principal.attributes['sub']")
  Match<TeamType, TeamType> update(Match<TeamType, TeamType> match);

}
