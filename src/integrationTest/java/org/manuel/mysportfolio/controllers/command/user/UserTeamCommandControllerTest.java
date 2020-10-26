package org.manuel.mysportfolio.controllers.command.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import javax.inject.Inject;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.ItConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.dtos.teamtousers.UserInTeamDto;
import org.manuel.mysportfolio.model.dtos.user.UserTeamDto;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam.UserInTeamRole;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.manuel.mysportfolio.repositories.TeamToUsersRepository;
import org.manuel.mysportfolio.transformers.teamtousers.UserInTeamDtoToUserInTeamTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(ItConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserTeamCommandControllerTest {

  @Inject
  private ObjectMapper objectMapper;

  @Inject
  private TeamRepository teamRepository;

  @Inject
  private TeamToUsersRepository teamToUsersRepository;

  @Inject
  private UserInTeamDtoToUserInTeamTransformer userInTeamDtoToUserInTeamTransformer;

  @Autowired
  private MockMvc mvc;

  @Test
  public void testSaveNewUserTeam() throws Exception {
    final var userTeamDto = UserTeamDto.builder()
        .team(TestUtils.createMockTeamDto())
        .userInTeam(
            UserInTeamDto.builder()
            .from(LocalDate.now())
            .role(UserInTeamRole.PLAYER)
            .build())
        .build();

    mvc.perform(post("/api/v1/users/me/teams")
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userTeamDto)))
        .andExpect(status().isCreated());
    assertEquals(1, teamRepository.count());
  }

  @Test
  public void testUpdateUserInTeamInUserTeam() throws Exception {
    final var newUserTeamDto = UserTeamDto.builder()
      .team(TestUtils.createMockTeamDto())
      .userInTeam(
          UserInTeamDto.builder()
            .from(LocalDate.now())
            .role(UserInTeamRole.PLAYER)
            .build())
      .build();

    final var savedUserTeams = objectMapper.readValue(mvc.perform(post("/api/v1/users/me/teams")
      .contentType(APPLICATION_JSON)
      .accept(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(newUserTeamDto)))
      .andExpect(status().isCreated())
      .andReturn().getResponse().getContentAsString(), UserTeamDto.class);

    final var updatedUserTeamDto = UserTeamDto.builder()
        .team(savedUserTeams.getTeam().toBuilder().createdBy(null).build())
        .userInTeam(
            UserInTeamDto.builder()
                .from(LocalDate.now().minusMonths(1))
                .role(UserInTeamRole.PLAYER)
                .role(UserInTeamRole.CAPTAIN)
                .build())
        .build();

    mvc.perform(put("/api/v1/users/me/teams")
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updatedUserTeamDto)))
        .andExpect(status().isOk());
    assertEquals(1, teamRepository.count());
    assertEquals(userInTeamDtoToUserInTeamTransformer.apply(updatedUserTeamDto.getUserInTeam()),
        teamToUsersRepository.findByTeamId(new ObjectId(savedUserTeams.getTeam().getId())).get().getUsers().get(ItConfiguration.IT_USER_ID));
  }

}