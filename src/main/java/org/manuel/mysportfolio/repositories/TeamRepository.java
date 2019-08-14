package org.manuel.mysportfolio.repositories;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends PagingAndSortingRepository<Team, ObjectId> {
}
