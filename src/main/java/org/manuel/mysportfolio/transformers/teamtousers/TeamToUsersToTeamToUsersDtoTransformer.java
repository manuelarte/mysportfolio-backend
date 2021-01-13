package org.manuel.mysportfolio.transformers.teamtousers;

import io.github.manuelarte.mysportfolio.model.documents.teamtouser.TeamToUsers;
import io.github.manuelarte.mysportfolio.model.dtos.teamtousers.TeamToUsersDto;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class TeamToUsersToTeamToUsersDtoTransformer implements
    Function<TeamToUsers, TeamToUsersDto> {

  private final UserInTeamToUserInTeamDtoTransformer userInTeamToUserInTeamDtoTransformer;

  @Override
  public TeamToUsersDto apply(final TeamToUsers teamToUsers) {
    final var users = teamToUsers.getUsers().entrySet().stream().collect(Collectors
        .toMap(Map.Entry::getKey, it -> userInTeamToUserInTeamDtoTransformer.apply(it.getValue())));
    return TeamToUsersDto.builder()
        .id(teamToUsers.getId())
        .version(teamToUsers.getVersion())
        .users(users)
        .admins(teamToUsers.getAdmins())
        .build();
  }

}
