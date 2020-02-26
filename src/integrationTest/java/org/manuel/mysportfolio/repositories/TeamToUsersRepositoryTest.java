package org.manuel.mysportfolio.repositories;

import javax.inject.Inject;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class TeamToUsersRepositoryTest {

    @Inject
    private TeamToUsersRepository teamToUsersRepository;

    @Test
    @DisplayName("save new user for a team")
    public void testUpdateUserInTeamNewUser() {
        final var teamId = new ObjectId();
        final String userId = "1234567";

        teamToUsersRepository.save(new TeamToUsers(null, null, teamId, Collections.emptyMap(), Collections.emptySet()));

        final var expected = new UserInTeam(LocalDate.now(), null, UserInTeam.UserInTeamRole.PLAYER);

        final var actual = teamToUsersRepository.updateUserInTeam(teamId, userId, expected);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("update user for a team")
    public void testUpdateUserInTeamUpdateUser() {
        final var teamId = new ObjectId();
        final String userId = "1234567";

        teamToUsersRepository.save(new TeamToUsers(null, null, teamId,
                Collections.singletonMap(userId, new UserInTeam(LocalDate.now(), null, UserInTeam.UserInTeamRole.PLAYER)),
                Collections.emptySet()));

        final var expected = new UserInTeam(LocalDate.now().minus(1, ChronoUnit.MONTHS), null, UserInTeam.UserInTeamRole.PLAYER, UserInTeam.UserInTeamRole.COACH);

        final var actual = teamToUsersRepository.updateUserInTeam(teamId, userId, expected);
        assertEquals(expected, actual);
    }


}
