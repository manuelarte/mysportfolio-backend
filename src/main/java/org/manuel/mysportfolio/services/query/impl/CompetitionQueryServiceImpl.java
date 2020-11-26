package org.manuel.mysportfolio.services.query.impl;

import io.github.manuelarte.mysportfolio.model.documents.competition.Competition;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.repositories.CompetitionRepository;
import org.manuel.mysportfolio.services.query.CompetitionQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.threeten.extra.Interval;

@Service
@lombok.AllArgsConstructor
class CompetitionQueryServiceImpl implements CompetitionQueryService {

  private final CompetitionRepository competitionRepository;

  @Override
  public Optional<Competition> findOne(final ObjectId id) {
    return competitionRepository.findById(id);
  }

  @Override
  public Page<Competition> findAllCreatedBy(final String createdBy, final Pageable pageable) {
    return competitionRepository.findAllByCreatedBy(createdBy, pageable);
  }

  @Override
  public Page<Competition> findAllByQueryAndCreatedBy(final Pageable pageable, final String createdBy, final Query query) {
    query.addCriteria(Criteria.where("createdBy").is(createdBy));
    return competitionRepository.findAllByQuery(pageable, query);
  }

  @Override
  public int countAllByCreatedByInInterval(final String createdBy, final Interval interval) {
    final var lowerLimit = interval.getStart();
    final var upperLimit = interval.getEnd();
    return competitionRepository
        .countAllByCreatedByAndCreatedDateIsBetween(createdBy, lowerLimit, upperLimit);
  }

  @Override
  public List<Competition> findAllPlaying(final DayOfWeek dayOfWeek) {
    return competitionRepository.findByDefaultMatchDayIs(dayOfWeek);
  }

}
