package org.manuel.mysportfolio.repositories;

import java.time.Instant;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface CompetitionRepository extends PagingAndSortingRepository<Competition, ObjectId> {

    Page<Competition> findAllByCreatedByIs(Pageable pageable, String createdBy);

    int countAllByCreatedByAndCreatedDateIsBetween(String createdBy, Instant lowerLimit, Instant upperLimit);

    List<Competition> findByDefaultMatchDayIs(DayOfWeek dayOfWeek);

}
