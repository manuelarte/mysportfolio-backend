package org.manuel.mysportfolio.transformers.teamtousers;

import org.manuel.mysportfolio.model.dtos.teamtousers.UserInTeamDto;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;
import org.springframework.stereotype.Component;

import java.util.function.Function;

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