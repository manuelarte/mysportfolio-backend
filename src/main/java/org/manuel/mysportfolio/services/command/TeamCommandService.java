package org.manuel.mysportfolio.services.command;

import org.manuel.mysportfolio.model.entities.team.Team;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public interface TeamCommandService {

    @PreAuthorize("hasRole('ROLE_USER') and @permissionsService.canSaveTeam()")
    Team save(Team team);

    @PostAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') and returnObject.createdBy.get() == authentication.principal.attributes['sub']")
    Team update(Team team);

}
