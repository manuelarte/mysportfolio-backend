package org.manuel.mysportfolio.services.command;

import org.manuel.mysportfolio.model.entities.Competition;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CompetitionCommandService {

  @PreAuthorize("hasRole('ROLE_USER') and @permissionsService.canSaveCompetition()")
  Competition save(Competition competition);

  // TODO Post authorize isn't working. It commits the change even that 403 is returned
  @PostAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') and returnObject.createdBy.get() == authentication.principal.attributes['sub']")
  Competition update(Competition competition);

}
