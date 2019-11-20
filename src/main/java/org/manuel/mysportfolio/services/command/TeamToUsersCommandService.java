package org.manuel.mysportfolio.services.command;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;
import org.manuel.mysportfolio.model.events.TeamCreatedEvent;
import org.springframework.security.access.prepost.PreAuthorize;

public interface TeamToUsersCommandService {

    // This method should not be called, since it's always done by the application event listener
    TeamToUsers save(TeamToUsers teamToUsers);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM' ) or (hasRole('ROLE_USER') and @permissionsService.isTeamAdmin(#teamToUsers.teamId))")
    TeamToUsers update(TeamToUsers teamToUsers);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM' ) or (hasRole('ROLE_USER') and " +
            "(@permissionsService.isTeamAdmin(#teamId) or @permissionsService.canSaveUserInTeam(#teamId, #userId)))")
    UserInTeam updateUserInTeam(ObjectId teamId, String userId, UserInTeam userInTeam);

    void handleTeamCreatedEvent(TeamCreatedEvent teamCreatedEvent);


}
