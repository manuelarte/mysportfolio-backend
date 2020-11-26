package org.manuel.mysportfolio.services.query.impl;

import io.github.manuelarte.mysportfolio.model.Sport;
import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.services.query.MatchQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.threeten.extra.Interval;

@Service
@lombok.AllArgsConstructor
class MatchQueryServiceImpl implements MatchQueryService {

  private final MatchRepository matchRepository;

  @Override
  public Optional<Match<TeamType, TeamType>> findOne(final ObjectId id) {
    return matchRepository.findById(id);
  }

  @Override
  public Page<Match<TeamType, TeamType>> findAllCreatedBy(final String createdBy, final Pageable pageable) {
    return matchRepository.findAllByCreatedBy(createdBy, pageable);
  }

  @Override
  public Page<Match<TeamType, TeamType>> findAllBy(final Query query,
      final Pageable pageable, final String createdBy) {
    query.addCriteria(Criteria.where("createdBy").is(createdBy));
    return matchRepository.findQueryAllCreatedBy(query, pageable);
  }

  @Override
  public int countAllByCreatedByInInterval(final String createdBy, final Interval interval) {
    return matchRepository.countAllByCreatedByAndCreatedDateIsBetween(createdBy, interval.getStart(), interval.getEnd());
  }

  @Override
  public Collection<Match<?, ?>> findAllByPlayedForContainsAndStartDateIsBetweenAndSportIs(
      final String userId,
      final LocalDate from, final LocalDate to, final Sport sport) {
    return matchRepository
        .findAllByTypeSportOrTypeCompetitionInAndStartDateIsBetween(userId, sport, from, to)
        .stream()
        .map(it -> (Match<?, ?>) it).collect(Collectors.toSet());
  }

}
