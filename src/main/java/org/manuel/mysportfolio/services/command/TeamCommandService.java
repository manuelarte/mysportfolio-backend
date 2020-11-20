package org.manuel.mysportfolio.services.command;

import io.github.manuelarte.mysportfolio.model.documents.team.Team;
import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PreAuthorize;

public interface TeamCommandService {

  @PreAuthorize("hasRole('ROLE_USER') and @permissionsService.canSaveTeam()")
  Team save(Team team);

  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') and @permissionsService.isTeamAdmin(#team.id)")
  Team update(Team team);

  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') and @permissionsService.isTeamAdmin(#id)")
  Team partialUpdate(ObjectId id, Team team);

}
