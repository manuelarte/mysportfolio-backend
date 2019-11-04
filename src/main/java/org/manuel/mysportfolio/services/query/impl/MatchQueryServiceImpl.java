package org.manuel.mysportfolio.services.query.impl;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.services.query.MatchQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MatchQueryServiceImpl implements MatchQueryService {

    private final MatchRepository matchRepository;

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
        query.addCriteria(Criteria.where("createdBy").is(createdBy));
        return matchRepository.findQueryAllCreatedBy(query, pageable);
    }

}
