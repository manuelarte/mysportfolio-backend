package org.manuel.mysportfolio.repositories;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends PagingAndSortingRepository<Match, ObjectId> {
}
