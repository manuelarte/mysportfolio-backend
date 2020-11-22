package org.manuel.mysportfolio.services.query.impl;

import io.github.manuelarte.mysportfolio.model.documents.team.Team;
import io.github.manuelarte.mysportfolio.model.documents.teamtouser.TeamToUsers;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.manuel.mysportfolio.services.query.TeamToUsersQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.threeten.extra.Interval;

@Service
@lombok.AllArgsConstructor
class TeamQueryServiceImpl implements TeamQueryService {

  private final TeamRepository teamRepository;
  private final TeamToUsersQueryService teamToUsersQueryService;

  @Override
  public Page<Team> findAllForUser(final Pageable pageable, final String userId) {
    final var byUsersExists = teamToUsersQueryService.findByUsersExists(userId);
    return this.findAllByIdsIn(pageable,
        byUsersExists.stream().map(TeamToUsers::getTeamId).collect(Collectors.toSet()));
  }

  @Override
  public Optional<Team> findOne(final ObjectId id) {
    return teamRepository.findById(id);
  }

  @Override
  public Page<Team> findAllCreatedBy(final String createdBy, final Pageable pageable) {
    return teamRepository.findAllByCreatedBy(createdBy, pageable);
  }

  @Override
  public Page<Team> findAllByIdsIn(final Pageable pageable, Set<ObjectId> ids) {
    return teamRepository.findAllByIdIsIn(pageable, ids);
  }

  @Override
  public int countAllByCreatedByInInterval(final String createdBy, final Interval interval) {
    final var lowerLimit = interval.getStart();
    final var upperLimit = interval.getEnd();
    return teamRepository.countAllByCreatedByAndCreatedDateIsBetween(createdBy, lowerLimit, upperLimit);
  }

}
