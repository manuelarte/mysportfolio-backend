package org.manuel.mysportfolio.transformers.teamtousers;

import io.github.manuelarte.mysportfolio.model.documents.teamtouser.TeamToUsers;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.teamtousers.TeamToUsersDto;
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
        .id(Optional.ofNullable(teamToUsers.getId()).map(ObjectId::toString).orElse(null))
        .version(teamToUsers.getVersion())
        .users(users)
        .admins(teamToUsers.getAdmins())
        .build();
  }

}
