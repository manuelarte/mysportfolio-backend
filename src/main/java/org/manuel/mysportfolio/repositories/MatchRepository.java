package org.manuel.mysportfolio.repositories;

import java.time.LocalDate;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository
    extends PagingAndSortingRepository<Match<? extends TeamType, ? extends TeamType>, ObjectId>,
    MatchRepositoryCustom {

  Page<Match<TeamType, TeamType>> findAllByCreatedByIs(Pageable pageable, String createdBy);

  int countAllByCreatedDateBetweenAndCreatedBy(LocalDate from, LocalDate to, String createdBy);

}
