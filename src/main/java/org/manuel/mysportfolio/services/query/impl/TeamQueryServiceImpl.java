package org.manuel.mysportfolio.services.query.impl;

import java.time.Year;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.manuel.mysportfolio.services.query.TeamToUsersQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class TeamQueryServiceImpl implements TeamQueryService {

  private final TeamRepository teamRepository;
  private final TeamToUsersQueryService teamToUsersQueryService;

  @Override
  public Page<Team> findAllForUser(final Pageable pageable, final String userId) {
    final var byUsersExists = teamToUsersQueryService.findByUsersExists(userId);
    return teamRepository.findAllByIdIsIn(pageable,
        byUsersExists.stream().map(TeamToUsers::getTeamId).collect(Collectors.toSet()));
  }

  @Override
  public Optional<Team> findOne(final ObjectId id) {
    return teamRepository.findById(id);
  }

  @Override
  public int countAllByCreatedByInYear(final String createdBy, final Year year) {
    final var lowerLimit = year.atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);
    final var upperLimit = year.plusYears(1).atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);
    return teamRepository
        .countAllByCreatedByAndCreatedDateIsBetween(createdBy, lowerLimit, upperLimit);
  }

}
