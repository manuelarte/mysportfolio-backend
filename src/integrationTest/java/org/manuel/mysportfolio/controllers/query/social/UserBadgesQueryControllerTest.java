package org.manuel.mysportfolio.controllers.query.social;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.ITConfiguration;
import org.manuel.mysportfolio.model.Badge;
import org.manuel.mysportfolio.model.entities.badges.UserBadges;
import org.manuel.mysportfolio.repositories.UserBadgesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Import(ITConfiguration.class)
@ExtendWith({SpringExtension.class})
public class UserBadgesQueryControllerTest {

    @Autowired
    private UserBadgesRepository userBadgesRepository;

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
        userBadgesRepository.deleteAll();
    }

    @Test
    public void testGetMyBadgesDefaultLocale() throws Exception {
        final String userId = "123456789";
        userBadgesRepository.save(new UserBadges(null, null,
            userId, Set.of(Badge.COMPETITION_FOOTBALL_FIRST_ADDED, Badge.FOOTBALL_FIRST_ASSIST)));

        mvc.perform(
            get("/api/v1/social/users/{userId}/badges", userId).contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[?(@.badge=='COMPETITION_FOOTBALL_FIRST_ADDED')].displayName", Matchers.contains("competition added")))
            .andExpect(jsonPath("$[?(@.badge=='FOOTBALL_FIRST_ASSIST')].displayName", Matchers.contains("first assist")));
    }

    @Test
    public void testGetMyBadgesEsLocale() throws Exception {
        final String userId = "123456789";
        userBadgesRepository.save(new UserBadges(null, null,
            userId, Set.of(Badge.COMPETITION_FOOTBALL_FIRST_ADDED, Badge.FOOTBALL_FIRST_ASSIST)));

        mvc.perform(
            get("/api/v1/social/users/{userId}/badges?locale=es", userId).contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[?(@.badge=='COMPETITION_FOOTBALL_FIRST_ADDED')].displayName", Matchers.contains("competición añadida")))
            .andExpect(jsonPath("$[?(@.badge=='FOOTBALL_FIRST_ASSIST')].displayName", Matchers.contains("primera asistencia")));
    }

}
