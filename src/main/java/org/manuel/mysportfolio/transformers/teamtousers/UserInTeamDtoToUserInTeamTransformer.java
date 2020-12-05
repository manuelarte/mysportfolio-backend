package org.manuel.mysportfolio.transformers.teamtousers;

import io.github.manuelarte.mysportfolio.model.documents.teamtouser.UserInTeam;
import io.github.manuelarte.mysportfolio.model.dtos.teamtousers.UserInTeamDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class UserInTeamDtoToUserInTeamTransformer implements Function<UserInTeamDto, UserInTeam> {

  @Override
  public UserInTeam apply(final UserInTeamDto userInTeamDto) {
    final var userInTeam = new UserInTeam();
    userInTeam.setFrom(userInTeamDto.getFrom());
    userInTeam.setTo(userInTeamDto.getTo());
    userInTeam.setRoles(userInTeamDto.getRoles());
    return userInTeam;
  }

}
