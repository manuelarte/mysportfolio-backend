package org.manuel.mysportfolio.services.query.impl;

import java.time.DayOfWeek;
import java.time.Year;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.Competition;
import org.manuel.mysportfolio.repositories.CompetitionRepository;
import org.manuel.mysportfolio.services.query.CompetitionQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@lombok.AllArgsConstructor
class CompetitionQueryServiceImpl implements CompetitionQueryService {

  private final CompetitionRepository competitionRepository;

  @Override
  public Optional<Competition> findOne(final ObjectId id) {
    return competitionRepository.findById(id);
  }

  @Override
  public Page<Competition> findAllCreatedBy(final Pageable pageable, final String createdBy) {
    return competitionRepository.findAllByCreatedByIs(pageable, createdBy);
  }

  @Override
  public Page<Competition> findAllByQueryAndCreatedBy(final Pageable pageable, final String createdBy, final Query query) {
    query.addCriteria(Criteria.where("createdBy").is(createdBy));
    return competitionRepository.findAllByQuery(pageable, query);
  }

  @Override
  public int countAllByCreatedByInYear(final String createdBy, final Year year) {
    final var lowerLimit = year.atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);
    final var upperLimit = year.plusYears(1).atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);
    return competitionRepository
        .countAllByCreatedByAndCreatedDateIsBetween(createdBy, lowerLimit, upperLimit);
  }

  @Override
  public List<Competition> findAllPlaying(final DayOfWeek dayOfWeek) {
    return competitionRepository.findByDefaultMatchDayIs(dayOfWeek);
  }

}
