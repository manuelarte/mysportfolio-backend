package org.manuel.mysportfolio.services.query.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.repositories.TeamToUsersRepository;
import org.manuel.mysportfolio.services.query.TeamToUsersQueryService;
import org.springframework.stereotype.Service;

@Service
@lombok.AllArgsConstructor
class TeamToUsersQueryServiceImpl implements TeamToUsersQueryService {

  private final TeamToUsersRepository teamToUsersRepository;

  @Override
  public Optional<TeamToUsers> findByTeamId(final ObjectId teamId) {
    return teamToUsersRepository.findByTeamId(teamId);
  }

  @Override
  public List<TeamToUsers> findByTeamIdIn(Collection<ObjectId> teamIds) {
    return teamToUsersRepository.findByTeamIdIn(teamIds);
  }

  @Override
  public List<TeamToUsers> findByUsersExists(final String userId) {
    return teamToUsersRepository.findByUsersExists(userId);
  }

}
