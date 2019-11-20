package org.manuel.mysportfolio.controllers.query;

import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.ITConfiguration;
import org.manuel.mysportfolio.model.dtos.usernotification.TeamAddUserNotificationDto;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;
import org.manuel.mysportfolio.model.entities.usernotification.TeamAddUserNotification;
import org.manuel.mysportfolio.model.entities.usernotification.UserNotification;
import org.manuel.mysportfolio.repositories.TeamToUsersRepository;
import org.manuel.mysportfolio.repositories.UserNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(ITConfiguration.class)
@ExtendWith({SpringExtension.class})
public class AppUserQueryControllerTest {

    @Autowired
    private UserNotificationRepository userNotificationRepository;

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
        userNotificationRepository.deleteAll();
    }

    @Test
    public void testGetMyNotifications() throws Exception {
        final String userId = "123456789";
        final var actual = new TeamAddUserNotification(null, null, "from", userId, new ObjectId());
        userNotificationRepository.save(actual);

        mvc.perform(get("/api/v1/users/me/notifications").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.content[0].type").value("team-add-user"));
    }


}
