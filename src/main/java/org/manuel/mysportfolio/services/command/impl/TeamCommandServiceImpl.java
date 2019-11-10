package org.manuel.mysportfolio.services.command.impl;

import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.manuel.mysportfolio.services.command.TeamCommandService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

@Service
@lombok.AllArgsConstructor
class TeamCommandServiceImpl implements TeamCommandService {

    private final TeamRepository teamRepository;

    @Override
    public Team save(@NotNull final Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team update(@NotNull final Team team) {
        Assert.notNull(team.getId(), "Can't update a new team");
        return teamRepository.save(team);
    }
}
