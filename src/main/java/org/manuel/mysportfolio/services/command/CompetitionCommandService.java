package org.manuel.mysportfolio.services.command;

import org.manuel.mysportfolio.model.entities.Competition;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CompetitionCommandService {

    @PreAuthorize("hasRole('ROLE_USER') and @permissionsService.canSaveCompetition()")
    Competition save(Competition competition);

}
