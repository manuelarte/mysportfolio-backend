package org.manuel.mysportfolio.services.query.impl;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.Competition;
import org.manuel.mysportfolio.repositories.CompetitionRepository;
import org.manuel.mysportfolio.services.query.CompetitionQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CompetitionQueryServiceImpl implements CompetitionQueryService {

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
    public int countAllByCreatedBy(final String createdBy) {
        return competitionRepository.countAllByCreatedBy(createdBy);
    }

}
