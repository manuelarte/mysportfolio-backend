package org.manuel.mysportfolio.controllers.query;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import javax.inject.Inject;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.ITConfiguration;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;
import org.manuel.mysportfolio.repositories.TeamToUsersRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Import(ITConfiguration.class)
@ExtendWith({SpringExtension.class})
public class TeamToUsersQueryControllerTest {

    @Inject
    private TeamToUsersRepository teamToUsersRepository;

    @Inject
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @AfterEach
    public void tearDown() {
        teamToUsersRepository.deleteAll();
    }

    @Test
    public void testGetTeamToUsersOfExistingEntry() throws Exception {
        final String userId = "123456789";
        final var actual = new TeamToUsers(null, null, new ObjectId(),
            Collections.singletonMap(userId,
                new UserInTeam(LocalDate.now().minus(1, ChronoUnit.MONTHS), null,
                    Collections.singleton(UserInTeam.UserInTeamRole.PLAYER))),
            Collections.singleton("123456789"));
        teamToUsersRepository.save(actual);

        mvc.perform(get("/api/v1/teams/{teamId}/users", actual.getTeamId().toString())
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", Matchers.notNullValue()))
            .andExpect(jsonPath("$.version").value(0))
            .andExpect(jsonPath("$.users." + userId + ".roles",
                Matchers.contains(UserInTeam.UserInTeamRole.PLAYER.toString())));
    }

    @Test
    public void testGetTeamToUsersOfNotExistingEntry() throws Exception {
        mvc.perform(get("/api/v1/teams/{teamId}/users", new ObjectId().toString())
            .contentType(APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

}
