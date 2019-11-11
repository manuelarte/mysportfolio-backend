package org.manuel.mysportfolio.services.query.impl;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.repositories.TeamToUsersRepository;
import org.manuel.mysportfolio.services.query.TeamToUsersQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@lombok.AllArgsConstructor
public class TeamToUsersQueryServiceImpl implements TeamToUsersQueryService {

    private final TeamToUsersRepository teamToUsersRepository;

    @Override
    public Optional<TeamToUsers> findByTeamId(final ObjectId teamId) {
        return teamToUsersRepository.findByTeamId(teamId);
    }

    @Override
    public List<TeamToUsers> findByUsersExists(final String userId) {
        return teamToUsersRepository.findByUsersExists(userId);
    }

}
