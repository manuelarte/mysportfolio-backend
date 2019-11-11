package org.manuel.mysportfolio.transformers.teamtousers;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.teamtousers.TeamToUsersDto;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class TeamToUsersToTeamToUsersDtoTransformer implements Function<TeamToUsers, TeamToUsersDto> {


    @Override
    public TeamToUsersDto apply(final TeamToUsers teamToUsers) {
        return TeamToUsersDto.builder()
                .id(Optional.ofNullable(teamToUsers.getId()).map(ObjectId::toString).orElse(null))
                .version(teamToUsers.getVersion())
                .teamId(teamToUsers.getTeamId().toString())
                .users(teamToUsers.getUsers())
                .admins(teamToUsers.getAdmins())
                .build();
    }

}
