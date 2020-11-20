package org.manuel.mysportfolio.repositories;

import io.github.manuelarte.mysportfolio.model.documents.teamtouser.TeamToUsers;
import io.github.manuelarte.mysportfolio.model.documents.teamtouser.UserInTeam;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamToUsersCustomRepository {

  List<TeamToUsers> findByUsersExists(String externalUserId);

  Page<TeamToUsers> findAllByUsersExists(Pageable pageable, String externalUserId);

  UserInTeam updateUserInTeam(ObjectId teamId, String userId, UserInTeam userInTeam);

}
