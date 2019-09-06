package org.manuel.mysportfolio.services.query.impl;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.manuel.mysportfolio.services.query.MatchQueryService;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeamQueryServiceImpl implements TeamQueryService {

    private final TeamRepository teamRepository;

    @Override
    public Optional<Team> findOne(final ObjectId id) {
        return teamRepository.findById(id);
    }

}
