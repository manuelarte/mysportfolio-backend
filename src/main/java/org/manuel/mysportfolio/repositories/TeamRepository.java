package org.manuel.mysportfolio.repositories;

import java.util.Set;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends PagingAndSortingRepository<Team, ObjectId> {

  Page<Team> findAllByIdIsIn(Pageable pageable, Set<ObjectId> ids);

  int countAllByCreatedBy(String createdBy);

}
