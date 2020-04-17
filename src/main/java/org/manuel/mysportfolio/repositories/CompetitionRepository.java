package org.manuel.mysportfolio.repositories;

import io.github.manuelarte.spring.manuelartevalidation.repositories.CrpudRepository;
import java.time.DayOfWeek;
import java.time.Instant;
import java.util.List;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository extends PagingAndSortingRepository<Competition, ObjectId>,
    CrpudRepository<Competition, ObjectId> {

  Page<Competition> findAllByCreatedByIs(Pageable pageable, String createdBy);

  int countAllByCreatedByAndCreatedDateIsBetween(String createdBy, Instant lowerLimit,
      Instant upperLimit);

  List<Competition> findByDefaultMatchDayIs(DayOfWeek dayOfWeek);

}
