package org.manuel.mysportfolio.services.query.impl;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TeamQueryServiceImpl implements TeamQueryService {

    private final TeamRepository teamRepository;

    @Override
    public Page<Team> findAllCreatedBy(final Pageable pageable, final String createdBy) {
        return teamRepository.findAllByCreatedByIs(pageable, createdBy);
    }

    @Override
    public Optional<Team> findOne(final ObjectId id) {
        return teamRepository.findById(id);
    }

    @Override
    public int countAllByCreatedBy(final String createdBy) {
        return teamRepository.countAllByCreatedBy(createdBy);
    }

}
