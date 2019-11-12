package org.manuel.mysportfolio.services.command.impl;

import io.jsonwebtoken.lang.Assert;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;
import org.manuel.mysportfolio.repositories.TeamToUsersRepository;
import org.manuel.mysportfolio.services.command.TeamToUsersCommandService;
import org.springframework.stereotype.Service;

@Service
@lombok.AllArgsConstructor
public class TeamToUsersCommandServiceImpl implements TeamToUsersCommandService {

    private final TeamToUsersRepository teamToUsersRepository;

    @Override
    public TeamToUsers save(final TeamToUsers teamToUsers) {
        Assert.isNull(teamToUsers.getId(), "The team to users entity already exists: " + teamToUsers.getId());
        return teamToUsersRepository.save(teamToUsers);
    }

    @Override
    public TeamToUsers update(final TeamToUsers teamToUsers) {
        Assert.notNull(teamToUsers.getId(), "Can't update a new team to users entity");
        return teamToUsersRepository.save(teamToUsers);
    }

    @Override
    public UserInTeam updateUserInTeam(final ObjectId teamId, final String userId, final UserInTeam userInTeam) {
        return teamToUsersRepository.updateUserInTeam(teamId, userId, userInTeam);
    }
}
