package org.manuel.mysportfolio.controllers.query;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.ItConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.entities.usernotification.TeamAddUserNotification;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.manuel.mysportfolio.repositories.UserNotificationRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Import(ItConfiguration.class)
public class AppUserQueryControllerTest {

  @Inject
  private TeamRepository teamRepository;

  @Inject
  private UserNotificationRepository userNotificationRepository;

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
    userNotificationRepository.deleteAll();
  }

  @Test
  public void testGetMyNotifications() throws Exception {
    final var teamSaved = teamRepository.save(TestUtils.createMockTeam());

    final String userId = "123456789";
    final var actual = new TeamAddUserNotification(null, null, "from", userId, teamSaved.getId());
    userNotificationRepository.save(actual);

    mvc.perform(get("/api/v1/users/me/notifications").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", Matchers.hasSize(1)))
        .andExpect(jsonPath("$.content[0].type").value("team-add-user"));
  }


}
