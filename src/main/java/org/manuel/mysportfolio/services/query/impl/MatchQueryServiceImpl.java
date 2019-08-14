package org.manuel.mysportfolio.services.query.impl;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.services.query.MatchQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MatchQueryServiceImpl implements MatchQueryService {

    private final MatchRepository matchRepository;

    @Override
    public Optional<Match> findOne(final ObjectId id) {
        return matchRepository.findById(id);
    }

    @Override
    public Page<Match> findAll(Pageable pageable) {
        return matchRepository.findAll(pageable);
    }

}
