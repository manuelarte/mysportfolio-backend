package org.manuel.mysportfolio.repositories;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.Competition;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository extends PagingAndSortingRepository<Competition, ObjectId> {

    Page<Competition> findAllByCreatedByIs(Pageable pageable, String createdBy);

    int countAllByCreatedBy(String createdBy);

}
