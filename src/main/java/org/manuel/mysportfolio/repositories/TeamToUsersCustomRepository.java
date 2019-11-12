package org.manuel.mysportfolio.repositories;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamToUsersCustomRepository {

    List<TeamToUsers> findByUsersExists(String userId);

    UserInTeam updateUserInTeam(ObjectId teamId, String userId, UserInTeam userInTeam);

}
