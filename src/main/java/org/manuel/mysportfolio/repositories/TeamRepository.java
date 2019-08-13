package org.manuel.mysportfolio.repositories;

import org.manuel.mysportfolio.model.entities.Team;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeamRepository extends PagingAndSortingRepository<Team, UUID> {
}
