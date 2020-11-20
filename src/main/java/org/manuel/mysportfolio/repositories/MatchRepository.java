package org.manuel.mysportfolio.repositories;

import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import java.time.LocalDate;
import org.bson.types.ObjectId;
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
