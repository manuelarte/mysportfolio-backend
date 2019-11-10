package org.manuel.mysportfolio.services.command.impl;

import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.services.command.MatchCommandService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@lombok.AllArgsConstructor
class MatchCommandServiceImpl implements MatchCommandService {

    private final MatchRepository matchRepository;

    @Override
    public Match<TeamType, TeamType> save(final Match<TeamType, TeamType> match) {
        Assert.notNull(match, "The match to save can't be null");
        return matchRepository.save(match);
    }

    @Override
    public Match<TeamType, TeamType> update(final Match<TeamType, TeamType> match) {
        Assert.notNull(match, "The match to save can't be null");
        Assert.notNull(match.getId(), "Can't update a new match");
        return matchRepository.save(match);
    }
}
