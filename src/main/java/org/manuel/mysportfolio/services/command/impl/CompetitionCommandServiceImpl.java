package org.manuel.mysportfolio.services.command.impl;

import io.github.manuelarte.mysportfolio.model.documents.competition.Competition;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.publishers.CompetitionCreatedEventPublisher;
import org.manuel.mysportfolio.repositories.CompetitionRepository;
import org.manuel.mysportfolio.services.command.CompetitionCommandService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@lombok.AllArgsConstructor
class CompetitionCommandServiceImpl implements CompetitionCommandService {

  private final CompetitionCreatedEventPublisher competitionCreatedEventPublisher;
  private final CompetitionRepository competitionRepository;

  @Override
  public Competition save(@NotNull final Competition competition) {
    final var saved = competitionRepository.save(competition);
    competitionCreatedEventPublisher.publishEvent(saved);
    return saved;
  }

  @Override
  public Competition update(@NotNull final Competition competition) {
    Assert.notNull(competition.getId(), "Can't update a new competition");
    return competitionRepository.save(competition);
  }

  @Override
  public Competition partialUpdate(final ObjectId id, final Competition partialCompetition) {
    Assert.isNull(partialCompetition.getId(), "The id in a partial update has to be null");
    return competitionRepository.partialSave(id, partialCompetition);
  }
}
