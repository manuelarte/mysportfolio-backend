package org.manuel.mysportfolio.services.command;

import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.springframework.security.access.prepost.PreAuthorize;

public interface MatchCommandService {

    @PreAuthorize("hasRole('ROLE_USER') and @permissionsService.canSaveMatch()")
    Match<TeamType, TeamType> save(Match<TeamType, TeamType> match);

}
