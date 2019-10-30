package org.manuel.mysportfolio.controllers.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.ITConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(ITConfiguration.class)
@ExtendWith({SpringExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MatchCommandControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @AfterEach
    public void tearDown() {
        matchRepository.deleteAll();
        teamRepository.deleteAll();
    }

    @Test
    public void testSaveMatchWithOneTeamRegistered() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(createAuthentication());
        final var teamSaved = teamRepository.save(TestUtils.createMockTeam());

        final var matchDto = TestUtils.createMockMatchDto(TestUtils.createMockRegisteredTeamDto(teamSaved.getId()),
                TestUtils.createMockAnonymousTeamDto(), 1, 2, Collections.singletonMap("123456789", TeamOption.HOME_TEAM));

        mvc.perform(post("/api/v1/matches").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(matchDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.sport").value(matchDto.getSport().name()))
                .andExpect(jsonPath("$.type").value(matchDto.getType().name()))
                .andExpect(jsonPath("$.homeTeam.type").value("registered"))
                .andExpect(jsonPath("$.homeTeam.teamId").value(matchDto.getHomeTeam().getTeamId()))
                .andExpect(jsonPath("$.awayTeam.type").value("anonymous"))
                .andExpect(jsonPath("$.awayTeam.name").value(matchDto.getAwayTeam().getName()));
    }

    @Test
    public void testSaveMatchWithTwoAnonymousTeams() throws Exception {
        final var matchDto = TestUtils.createMockMatchDto(TestUtils.createMockAnonymousTeamDto(),
                TestUtils.createMockAnonymousTeamDto(), 0, 0, Collections.singletonMap("123456789", TeamOption.HOME_TEAM));

        mvc.perform(post("/api/v1/matches").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(matchDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.sport").value(matchDto.getSport().name()))
                .andExpect(jsonPath("$.type").value(matchDto.getType().name()))
                .andExpect(jsonPath("$.homeTeam.type").value("anonymous"))
                .andExpect(jsonPath("$.homeTeam.name").value(matchDto.getHomeTeam().getName()))
                .andExpect(jsonPath("$.awayTeam.type").value("anonymous"))
                .andExpect(jsonPath("$.awayTeam.name").value(matchDto.getAwayTeam().getName()))
                .andExpect(jsonPath("$.events[*].id", Matchers.notNullValue()));
    }

    private Authentication createAuthentication() {
        final var authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        final var attributes = new HashMap<String, Object>();
        attributes.put("sub", 123456789);
        attributes.put("email_verified", true);
        attributes.put("iss", "https://accounts.google.com");
        attributes.put("given_name", "Test");
        attributes.put("family_name", "Not Production");
        attributes.put("name", "Test Not Production");
        attributes.put("email", "test@test.com");
        attributes.put("picture", "https://lh3.googleusercontent.com/a-/AAuE7mBk0hY2RSA_JMUDFNo2wT54GjycNKMGgtLfw5X1LpQ=s96-c");
        final var principal = new DefaultOAuth2User(new HashSet<>(authorities), attributes, "name");
        return new OAuth2AuthenticationToken(principal, authorities, "clientRegistrationId");
    }

}
