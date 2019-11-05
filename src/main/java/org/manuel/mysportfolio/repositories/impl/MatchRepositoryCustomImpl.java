package org.manuel.mysportfolio.repositories.impl;

import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.manuel.mysportfolio.repositories.MatchRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
}
