package org.manuel.mysportfolio.repositories;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamToUsersRepository extends CrudRepository<TeamToUsers, ObjectId>, TeamToUsersCustomRepository {

    Optional<TeamToUsers> findByTeamId(ObjectId teamId);

    List<TeamToUsers> findByTeamIdIn(Collection<ObjectId> teamIds);

    // boolean findByTeamIdAndUsersContains(ObjectId teamId, String userId);

}