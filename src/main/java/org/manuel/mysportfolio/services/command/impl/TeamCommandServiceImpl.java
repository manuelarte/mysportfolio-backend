package org.manuel.mysportfolio.services.command.impl;

import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.publishers.TeamCreatedEventPublisher;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.manuel.mysportfolio.services.command.TeamCommandService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@lombok.AllArgsConstructor
class TeamCommandServiceImpl implements TeamCommandService {

  private final TeamCreatedEventPublisher teamCreatedEventPublisher;
  private final TeamRepository teamRepository;

  @Override
  public Team save(@NotNull final Team team) {
    Assert.notNull(team, "Team can't be null");
    Assert.isNull(team.getId(), "Team must be new");
    final var saved = teamRepository.save(team);
    teamCreatedEventPublisher.publishEvent(saved);
    return saved;
  }

  @Override
  public Team update(@NotNull final Team team) {
    Assert.notNull(team.getId(), "Can't update a new team");
    return teamRepository.save(team);
  }

  @Override
  public Team partialUpdate(final ObjectId id, final Team partialTeam) {
    Assert.isNull(partialTeam.getId(), "The id in a partial update has to be null");
    return teamRepository.partialSave(id, partialTeam);
  }
}
