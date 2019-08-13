package org.manuel.mysportfolio.repositories;

import org.manuel.mysportfolio.model.entities.Match;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MatchRepository extends PagingAndSortingRepository<Match, UUID> {
}
