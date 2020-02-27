package org.manuel.mysportfolio.services.query.impl;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.manuel.mysportfolio.services.query.TeamToUsersQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@lombok.AllArgsConstructor
class TeamQueryServiceImpl implements TeamQueryService {

    private final TeamRepository teamRepository;
    private final TeamToUsersQueryService teamToUsersQueryService;

    @Override
    public Page<Team> findAllForUser(final Pageable pageable, final String userId) {
        final var byUsersExists = teamToUsersQueryService.findByUsersExists(userId);
        return teamRepository.findAllByIdIsIn(pageable, byUsersExists.stream().map(TeamToUsers::getTeamId).collect(Collectors.toSet()));
    }

    @Override
    public Optional<Team> findOne(final ObjectId id) {
        return teamRepository.findById(id);
    }

    @Override
    public int countAllByCreatedBy(final String createdBy) {
        return teamRepository.countAllByCreatedBy(createdBy);
    }

}
