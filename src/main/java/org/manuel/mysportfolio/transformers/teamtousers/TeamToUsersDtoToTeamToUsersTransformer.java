package org.manuel.mysportfolio.transformers.teamtousers;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.teamtousers.TeamToUsersDto;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Component
@lombok.AllArgsConstructor
public class TeamToUsersDtoToTeamToUsersTransformer implements BiFunction<ObjectId, TeamToUsersDto, TeamToUsers> {

    private final UserInTeamDtoToUserInTeamTransformer userInTeamDtoToUserInTeamTransformer;

    @Override
    public TeamToUsers apply(final ObjectId teamId, final TeamToUsersDto teamToUsersDto) {
        final var users = teamToUsersDto.getUsers().entrySet().stream().collect(Collectors.toMap(it -> it.getKey(), it -> userInTeamDtoToUserInTeamTransformer.apply(it.getValue())));
        final var teamToUsers = new TeamToUsers();
        teamToUsers.setId(Optional.ofNullable(teamToUsersDto.getId()).map(it -> new ObjectId(it)).orElse(null));
        teamToUsers.setVersion(teamToUsersDto.getVersion());
        teamToUsers.setTeamId(teamId);
        teamToUsers.setUsers(users);
        teamToUsers.setAdmins(teamToUsersDto.getAdmins());
        return teamToUsers;
    }

}
