package org.manuel.mysportfolio.controllers.command;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.manuelarte.mysportfolio.model.documents.teamtouser.TeamToUsers;
import io.github.manuelarte.mysportfolio.model.documents.teamtouser.UserInTeam;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Objects;
import javax.inject.Inject;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.ItConfiguration;
import org.manuel.mysportfolio.model.dtos.teamtousers.TeamToUsersDto;
import org.manuel.mysportfolio.model.dtos.teamtousers.UserInTeamDto;
import org.manuel.mysportfolio.repositories.TeamToUsersRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Import(ItConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TeamToUsersCommandControllerTest {

  @Inject
  private ObjectMapper objectMapper;

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
  public void testSaveTeamToUsers() throws Exception {
    final var userId = "123456";
    final var teamToUsers = TeamToUsersDto.builder()
        .users(Collections.singletonMap(userId,
            UserInTeamDto.builder().from(LocalDate.now().minus(1, ChronoUnit.MONTHS))
                .role(UserInTeam.UserInTeamRole.PLAYER)
                .build()))
        .admin(userId)
        .build();

    final var teamId = new ObjectId().toString();
    mvc.perform(post("/api/v1/teams/{teamId}/users", teamId).contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(teamToUsers)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", Matchers.notNullValue()))
        .andExpect(jsonPath("$.users." + userId + ".roles",
            Matchers.contains(UserInTeam.UserInTeamRole.PLAYER.toString())))
        .andExpect(jsonPath("$.admins", Matchers.contains(userId)));
  }

  @Test
  public void testSaveTeamToUsersNoAdminGiven() throws Exception {
    final var userId = "123456";
    final var teamToUsers = TeamToUsersDto.builder()
        .users(Collections.singletonMap(userId,
            UserInTeamDto.builder().from(LocalDate.now().minus(1, ChronoUnit.MONTHS))
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
    final var userId = "123456789";
    final var userInTeam = new UserInTeam();
    userInTeam.setFrom(LocalDate.now().minus(1, ChronoUnit.MONTHS));
    userInTeam.setRoles(Collections.singleton(UserInTeam.UserInTeamRole.PLAYER));

    final var teamId = new ObjectId();
    final var teamToUsers = new TeamToUsers();
    teamToUsers.setTeamId(teamId);
    teamToUsers.setUsers(Collections.singletonMap(userId, userInTeam));
    teamToUsers.setAdmins(Collections.singleton(userId));
    teamToUsersRepository.save(teamToUsers);

    final var expected = UserInTeamDto.builder()
        .from(userInTeam.getFrom())
        .to(LocalDate.now())
        .roles(userInTeam.getRoles())
        .build();

    final var rolesInArray = expected.getRoles().stream().map(Objects::toString).toArray();
    mvc.perform(put("/api/v1/teams/{teamId}/users/{userId}", teamId.toString(), userId)
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(expected)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.from").value(expected.getFrom().toString()))
        .andExpect(jsonPath("$.to").value(expected.getTo().toString()))
        .andExpect(jsonPath("$.roles",
            Matchers.containsInAnyOrder(rolesInArray)));
  }

  @Test
  public void testUpdateUserInTeamUserNotAllowed() throws Exception {
    final var userId = "differentUser";
    final var userInTeam = new UserInTeam();
    userInTeam.setFrom(LocalDate.now().minus(1, ChronoUnit.MONTHS));
    userInTeam.setRoles(Collections.singleton(UserInTeam.UserInTeamRole.PLAYER));

    final var teamId = new ObjectId();
    final var teamToUsers = new TeamToUsers();
    teamToUsers.setTeamId(teamId);
    teamToUsers.setUsers(Collections.singletonMap(userId, userInTeam));
    teamToUsers.setAdmins(Collections.singleton(userId));
    teamToUsersRepository.save(teamToUsers);

    final var expected = UserInTeamDto.builder()
        .from(userInTeam.getFrom())
        .to(LocalDate.now())
        .roles(userInTeam.getRoles())
        .build();

    mvc.perform(put("/api/v1/teams/{teamId}/users/{userId}", teamId.toString(), userId)
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(expected)))
        .andExpect(status().isForbidden());
  }


}
