package org.manuel.mysportfolio.controllers.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.ITConfiguration;
import org.manuel.mysportfolio.model.dtos.teamtousers.TeamToUsersDto;
import org.manuel.mysportfolio.model.dtos.teamtousers.UserInTeamDto;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;
import org.manuel.mysportfolio.repositories.TeamToUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(ITConfiguration.class)
@ExtendWith({SpringExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TeamToUsersCommandControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TeamToUsersRepository teamToUsersRepository;

    @Autowired
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
    public void testSaveTeamToUsers() throws Exception {
        final var userId = "123456";
        final var teamToUsers = TeamToUsersDto.builder()
                .users(Collections.singletonMap(userId, UserInTeamDto.builder().from(LocalDate.now().minus(1, ChronoUnit.MONTHS))
                        .role(UserInTeam.UserInTeamRole.PLAYER)
                        .build()))
                .admin(userId)
                .build();

        final var teamId = new ObjectId().toString();
        mvc.perform(post("/api/v1/teams/{teamId}/users", teamId).contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teamToUsers)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.users." + userId + ".roles", Matchers.contains(UserInTeam.UserInTeamRole.PLAYER.toString())))
                .andExpect(jsonPath("$.admins", Matchers.contains(userId)));
    }

    @Test
    public void testSaveTeamToUsersNoAdminGiven() throws Exception {
        final var userId = "123456";
        final var teamToUsers = TeamToUsersDto.builder()
                .users(Collections.singletonMap(userId, UserInTeamDto.builder().from(LocalDate.now().minus(1, ChronoUnit.MONTHS))
                        .role(UserInTeam.UserInTeamRole.PLAYER)
                        .build()))
                .build();

        final var teamId = new ObjectId().toString();
        mvc.perform(post("/api/v1/teams/{teamId}/users", teamId).contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teamToUsers)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testUpdateUserInTeam() throws Exception {
        final var userId = "123456";
        final var userInTeam = new UserInTeam();
        userInTeam.setFrom(LocalDate.now().minus(1, ChronoUnit.MONTHS));
        userInTeam.setRoles(Collections.singleton(UserInTeam.UserInTeamRole.PLAYER));

        final var teamToUsers = new TeamToUsers();
        teamToUsers.setUsers(Collections.singletonMap(userId, userInTeam));
        teamToUsers.setAdmins(Collections.singleton(userId));
        teamToUsersRepository.save(teamToUsers);

        final var expected = UserInTeamDto.builder()
                .from(userInTeam.getFrom())
                .to(LocalDate.now())
                .roles(userInTeam.getRoles())
                .build();

        final var teamId = new ObjectId().toString();
        final var rolesInArray = expected.getRoles().stream().map(Objects::toString).collect(Collectors.toList()).toArray();
        mvc.perform(put("/api/v1/teams/{teamId}/users/{userId}", teamId, userId).contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.from").value(expected.getFrom().toString()))
                .andExpect(jsonPath("$.to").value(expected.getTo().toString()))
                .andExpect(jsonPath("$.roles",
                        Matchers.containsInAnyOrder(rolesInArray)));
    }


}
