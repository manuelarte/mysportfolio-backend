package org.manuel.mysportfolio.repositories;

import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamToUsersCustomRepository {

    List<TeamToUsers> findByUsersExists(String userId);

}
