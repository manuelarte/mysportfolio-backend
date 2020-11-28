package org.manuel.mysportfolio.transformers.teamtousers;

import io.github.manuelarte.mysportfolio.model.documents.teamtouser.TeamToUsers;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.teamtousers.TeamToUsersDto;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class TeamToUsersDtoToTeamToUsersTransformer implements
    BiFunction<ObjectId, TeamToUsersDto, TeamToUsers> {

  private final UserInTeamDtoToUserInTeamTransformer userInTeamDtoToUserInTeamTransformer;

  @Override
  public TeamToUsers apply(final ObjectId teamId, final TeamToUsersDto teamToUsersDto) {
    final var users = teamToUsersDto.getUsers().entrySet().stream().collect(Collectors
        .toMap(Map.Entry::getKey, it -> userInTeamDtoToUserInTeamTransformer.apply(it.getValue())));
    final var teamToUsers = new TeamToUsers();
    teamToUsers.setId(teamToUsersDto.getId());
    teamToUsers.setVersion(teamToUsersDto.getVersion());
    teamToUsers.setTeamId(teamId);
    teamToUsers.setUsers(users);
    teamToUsers.setAdmins(teamToUsersDto.getAdmins());
    return teamToUsers;
  }

}
