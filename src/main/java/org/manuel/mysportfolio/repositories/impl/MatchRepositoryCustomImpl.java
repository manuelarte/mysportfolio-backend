package org.manuel.mysportfolio.repositories.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
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
    public Page<Match<TeamType, TeamType>> findQueryAllCreatedBy(final Query query, final Pageable pageable) {
        query.with(pageable);
        final List<Match<TeamType, TeamType>> list = mongoTemplate.find(query, Match.class).stream().map(m -> (Match<TeamType, TeamType>) m).collect(Collectors.toList());
        return PageableExecutionUtils.getPage(list, pageable,
                () -> mongoTemplate.count(query, Match.class));
    }

    @Override
    public List<Match> findAllByPlayedContainsAndSportIsAndStartDateIsBetween(final String externalId, final Sport sport, final LocalDate from, final LocalDate to) {
        final var usersMatches = Criteria.where("playedFor." + externalId).exists(true);
        final var ofSport = Criteria.where("sport").is(sport);
        final var betweenDates = Criteria.where("startDate").gte(from).lte(to);
        final var query = new Query(usersMatches.andOperator(ofSport, betweenDates));
        return mongoTemplate.find(query, Match.class);
    }

}
