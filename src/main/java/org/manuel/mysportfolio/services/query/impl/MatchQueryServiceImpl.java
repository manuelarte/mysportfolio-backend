package org.manuel.mysportfolio.services.query.impl;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.services.query.MatchQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MatchQueryServiceImpl implements MatchQueryService {

    private final MatchRepository matchRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<Match<? extends TeamType, ? extends TeamType>> findOne(final ObjectId id) {
        return matchRepository.findById(id);
    }

    @Override
    public Page<Match<TeamType, TeamType>> findAllCreatedBy(final Pageable pageable, final String userId) {
        return matchRepository.findAllByCreatedByIs(pageable, userId);
    }

    @Override
    public Page<Match<TeamType, TeamType>> findQueryAllCreatedBy(final Query query, final Pageable pageable, final String createdBy) {
        query.with(pageable);
        query.addCriteria(Criteria.where("createdBy").is(createdBy));
        List<Match<TeamType, TeamType>> list = mongoTemplate.find(query, Match.class).stream().map(m -> (Match<TeamType, TeamType>) m).collect(Collectors.toList());
        return PageableExecutionUtils.getPage(list, pageable,
                () -> mongoTemplate.count(query, Match.class));
    }

}
