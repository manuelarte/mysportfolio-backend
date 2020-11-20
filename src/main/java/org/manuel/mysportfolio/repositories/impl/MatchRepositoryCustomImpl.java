package org.manuel.mysportfolio.repositories.impl;

import io.github.manuelarte.mysportfolio.model.Sport;
import io.github.manuelarte.mysportfolio.model.documents.Competition;
import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.repositories.MatchRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
class MatchRepositoryCustomImpl implements MatchRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  @Override
  public Page<Match<TeamType, TeamType>> findQueryAllCreatedBy(final Query query,
      final Pageable pageable) {
    query.with(pageable);
    final List<Match<TeamType, TeamType>> list = mongoTemplate.find(query, Match.class).stream()
        .map(m -> (Match<TeamType, TeamType>) m).collect(Collectors.toList());
    return PageableExecutionUtils.getPage(list, pageable,
        () -> mongoTemplate.count(query, Match.class));
  }

  @Override
  public List<Match> findAllByTypeSportOrTypeCompetitionInAndStartDateIsBetween(
      final String externalId, final Sport sport,
      final LocalDate from, final LocalDate to) {
    final var usersMatches = Criteria.where("playedFor." + externalId).exists(true);
    final var betweenDates = Criteria.where("startDate").gte(from).lte(to);
    final var ofSport = Criteria.where("type.sport").is(sport.name());

    final var allCompetitions = mongoTemplate
        .findDistinct(new Query(Criteria.where("playedFor." + externalId).exists(true)
                .andOperator(Criteria.where("startDate").gte(from).lte(to),
                    Criteria.where("type.competitionId").exists(true))), "type.competitionId",
            Match.class,
            ObjectId.class);
    final var sportCompetitions = mongoTemplate
        .findDistinct(new Query(Criteria.where("id").in(allCompetitions)
                .andOperator(Criteria.where("sport").is(sport.name()))),
            "id", Competition.class, ObjectId.class);

    final var sportCompetitionIds = Criteria.where("type.competitionId").in(sportCompetitions);
    final var query = new Query(
        usersMatches.andOperator(betweenDates).orOperator(ofSport, sportCompetitionIds));
    return mongoTemplate.find(query, Match.class);
  }

}
