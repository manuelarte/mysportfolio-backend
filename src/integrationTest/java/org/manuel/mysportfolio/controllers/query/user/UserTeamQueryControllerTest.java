package org.manuel.mysportfolio.controllers.query.user;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.manuelarte.mysportfolio.model.documents.user.AppMembership;
import io.github.manuelarte.mysportfolio.model.documents.user.AppSettings;
import io.github.manuelarte.mysportfolio.model.documents.user.AppUser;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.ItConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.repositories.AppUserRepository;
import org.manuel.mysportfolio.services.command.TeamCommandService;
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
class UserTeamQueryControllerTest {

  @Autowired
  private AppUserRepository appUserRepository;

  @Autowired
  private TeamCommandService teamCommandService;

  @Autowired
  private MockMvc mvc;

  @BeforeEach
  @SuppressWarnings("checkstyle:javadoctype")
  public void setUp() {
    appUserRepository.save(new AppUser("test", "test@mysportfolio.org",
        ItConfiguration.IT_USER_ID, AppMembership.FREE,
        false, null, new AppSettings(false)));

  }

  @Test
  public void testGetPageUserTeamsForOneTeam() throws Exception {
    final var externalUserId = ItConfiguration.IT_USER_ID;

    // creating one team
    final var saved = TestUtils.doWithUserAuthentication(() -> teamCommandService.save(TestUtils.createMockTeam()));
    mvc.perform(get("/api/v1/users/{externalUserId}/teams", externalUserId)
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andExpect(jsonPath("$.content[0].team.id").value(saved.getId().toString()));
  }

  @Test
  public void testGetPageUserTeamsForMoreTeams() throws Exception {
    final var externalUserId = ItConfiguration.IT_USER_ID;

    // creating some teams
    final var teams = TestUtils.doWithUserAuthentication(
        () -> IntStream.range(0, 2).mapToObj(it -> teamCommandService.save(TestUtils.createMockTeam())).collect(Collectors.toList()));
    mvc.perform(get("/api/v1/users/{externalUserId}/teams", externalUserId)
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(teams.size())))
        .andExpect(jsonPath("$.content[0].team.id", Matchers.is(Matchers.in(teams.stream().map(t -> t.getId().toString())
            .collect(Collectors.toList())))))
        .andExpect(jsonPath("$.content[1].team.id", Matchers.is(Matchers.in(teams.stream().map(t -> t.getId().toString())
            .collect(Collectors.toList())))));
  }


  // test with query
  @Test
  public void testGetPageUserTeamsQTo() throws Exception {
    final var externalUserId = ItConfiguration.IT_USER_ID;

    // creating some teams
    final var teams = TestUtils.doWithUserAuthentication(
        () -> IntStream.range(0, 2).mapToObj(it -> teamCommandService.save(TestUtils.createMockTeam())).collect(Collectors.toList()));
    final String q = "to:!:(null);to:>2020-01-01";
    mvc.perform(get("/api/v1/users/{externalUserId}/teams?q={q}", externalUserId, q)
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(teams.size())))
        .andExpect(jsonPath("$.content[0].team.id", Matchers.is(Matchers.in(teams.stream().map(t -> t.getId().toString()).collect(Collectors.toList())))))
        .andExpect(jsonPath("$.content[1].team.id", Matchers.is(Matchers.in(teams.stream().map(t -> t.getId().toString()).collect(Collectors.toList())))));
  }

}
