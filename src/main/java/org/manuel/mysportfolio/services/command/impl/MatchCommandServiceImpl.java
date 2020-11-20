package org.manuel.mysportfolio.services.command.impl;

import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import org.manuel.mysportfolio.publishers.MatchCreatedEventPublisher;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.services.command.MatchCommandService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@lombok.AllArgsConstructor
class MatchCommandServiceImpl implements MatchCommandService {

  private final MatchRepository matchRepository;
  private final MatchCreatedEventPublisher matchCreatedEventPublisher;

  @Override
  public Match<TeamType, TeamType> save(final Match<TeamType, TeamType> match) {
    Assert.notNull(match, "The match to save can't be null");
    final var saved = matchRepository.save(match);
    matchCreatedEventPublisher.publishEvent(saved);
    return saved;
  }

  @Override
  public Match<TeamType, TeamType> update(final Match<TeamType, TeamType> match) {
    Assert.notNull(match, "The match to save can't be null");
    Assert.notNull(match.getId(), "Can't update a new match");
    return matchRepository.save(match);
  }
}
