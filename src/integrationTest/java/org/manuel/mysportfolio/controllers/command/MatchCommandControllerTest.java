package org.manuel.mysportfolio.controllers.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.dtos.team.AnonymousTeamDto;
import org.manuel.mysportfolio.model.dtos.team.RegisteredTeamDto;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
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
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    public void tearDown() {
        matchRepository.deleteAll();
        teamRepository.deleteAll();
    }

    @Test
    public void testSaveMatchWithOneTeamRegistered() throws Exception {
        final var teamSaved = teamRepository.save(TestUtils.createMockTeam());

        final var matchDto = TestUtils.createMockMatchDto(TestUtils.createMockRegisteredTeamDto(teamSaved.getId()),
                TestUtils.createMockAnonymousTeamDto());

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
                TestUtils.createMockAnonymousTeamDto());

        mvc.perform(post("/api/v1/matches").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(matchDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.sport").value(matchDto.getSport().name()))
                .andExpect(jsonPath("$.type").value(matchDto.getType().name()))
                .andExpect(jsonPath("$.homeTeam.type").value("anonymous"))
                .andExpect(jsonPath("$.homeTeam.name").value(matchDto.getHomeTeam().getName()))
                .andExpect(jsonPath("$.awayTeam.type").value("anonymous"))
                .andExpect(jsonPath("$.awayTeam.name").value(matchDto.getAwayTeam().getName()));
    }

}
