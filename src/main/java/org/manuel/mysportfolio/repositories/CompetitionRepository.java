package org.manuel.mysportfolio.repositories;

import io.github.manuelarte.mysportfolio.model.documents.competition.Competition;
import io.github.manuelarte.spring.manuelartevalidation.repositories.CrpudRepository;
import java.time.DayOfWeek;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository extends PagingAndSortingRepository<Competition, ObjectId>,
    CrpudRepository<Competition, ObjectId>, CompetitionRepositoryCustom, BaseDocumentRepository<Competition> {

  List<Competition> findByDefaultMatchDayIs(DayOfWeek dayOfWeek);

}
