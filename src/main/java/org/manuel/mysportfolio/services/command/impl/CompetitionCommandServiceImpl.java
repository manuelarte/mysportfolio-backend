package org.manuel.mysportfolio.services.command.impl;

import org.manuel.mysportfolio.model.entities.Competition;
import org.manuel.mysportfolio.repositories.CompetitionRepository;
import org.manuel.mysportfolio.services.command.CompetitionCommandService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

@Service
@lombok.AllArgsConstructor
class CompetitionCommandServiceImpl implements CompetitionCommandService {

    private final CompetitionRepository competitionRepository;

    @Override
    public Competition save(@NotNull final Competition competition) {
        return competitionRepository.save(competition);
    }

    @Override
    public Competition update(@NotNull final Competition competition) {
        Assert.notNull(competition.getId(), "Can't update a new competition");
        return competitionRepository.save(competition);
    }

}
