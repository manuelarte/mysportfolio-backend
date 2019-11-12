package org.manuel.mysportfolio.services.command;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;

public interface TeamToUsersCommandService {

    // This method should not be called, since it's always done by the application event listener
    TeamToUsers save(TeamToUsers teamToUsers);

    // TODO
    TeamToUsers update(TeamToUsers teamToUsers);

    // TODO the player should exist, and the player should not be allowed to change its role
    UserInTeam updateUserInTeam(ObjectId teamId, String userId, UserInTeam userInTeam);


}
