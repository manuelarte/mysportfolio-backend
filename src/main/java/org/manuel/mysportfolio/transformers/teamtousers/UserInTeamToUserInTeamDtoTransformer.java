package org.manuel.mysportfolio.transformers.teamtousers;

import io.github.manuelarte.mysportfolio.model.documents.teamtouser.UserInTeam;
import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.teamtousers.UserInTeamDto;
import org.springframework.stereotype.Component;

@Component
public class UserInTeamToUserInTeamDtoTransformer implements Function<UserInTeam, UserInTeamDto> {


  @Override
  public UserInTeamDto apply(final UserInTeam userInTeam) {
    if (userInTeam == null) {
      return null;
    }
    return UserInTeamDto.builder()
        .from(userInTeam.getFrom())
        .to(userInTeam.getTo())
        .roles(userInTeam.getRoles())
        .build();
  }

}
