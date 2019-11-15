package org.manuel.mysportfolio.transformers.teamtousers;

import org.manuel.mysportfolio.model.dtos.teamtousers.UserInTeamDto;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;
import org.springframework.stereotype.Component;

import java.util.function.Function;

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
