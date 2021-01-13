package org.manuel.mysportfolio.controllers.query.user;

import static org.manuel.mysportfolio.TestUtils.createMockAnonymousTeam;
import static org.manuel.mysportfolio.TestUtils.createMockRegisteredTeam;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.manuelarte.mysportfolio.model.documents.user.AppMembership;
import io.github.manuelarte.mysportfolio.model.documents.user.AppSettings;
import io.github.manuelarte.mysportfolio.model.documents.user.AppUser;
import java.time.YearMonth;
import javax.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.ItConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.repositories.AppUserRepository;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(ItConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserPlayerProfileQueryControllerTest {

  @Inject
  private AppUserRepository appUserRepository;

  @Inject
  private MatchRepository matchRepository;

  @Inject
  private MockMvc mvc;

  @BeforeEach
  @SuppressWarnings("checkstyle:javadoctype")
  public void setUp() {
    appUserRepository.save(new AppUser("test", "test@mysportfolio.org",
        ItConfiguration.IT_USER_ID, AppMembership.FREE, false, null,
        new AppSettings(false)));
  }

  @AfterEach
  public void tearDown() {
    matchRepository.deleteAll();
    appUserRepository.deleteAll();
  }

  @Test
  public void getSeasonSummaryForPlayer() throws Exception {
    final var userId = ItConfiguration.IT_USER_ID;
    matchRepository.save(TestUtils.createMockMatch(createMockRegisteredTeam(), createMockAnonymousTeam(), userId));

    final YearMonth now = YearMonth.now();
    mvc.perform(get("/api/v1/users/{userId}/player", userId)
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.info." + now.getYear() + ".football.summary." + now.getMonth() + ".numberOfMatchesPlayed")
            .value(1))
        .andExpect(jsonPath("$.info." + now.getYear() + ".football.summary." + now.getMonth() + ".numberOfGoals")
            .value(0))
        .andExpect(jsonPath("$.info." + now.plusYears(1).getYear()).doesNotExist());
  }

}
