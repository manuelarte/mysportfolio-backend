package org.manuel.mysportfolio.controllers.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.ITConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(ITConfiguration.class)
@ExtendWith({SpringExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TeamCommandControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TeamRepository teamRepository;

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
        teamRepository.deleteAll();
    }

    @Test
    public void testSaveTeamAllFieldsValid() throws Exception {
        final var teamDto = TestUtils.createMockTeamDto();

        mvc.perform(post("/api/v1/teams").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teamDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.name").value(teamDto.getName()));
    }

    @Test
    public void testSaveTeamNoNameGiven() throws Exception {
        final var teamDtoWithoutName = TestUtils.createMockTeamDto().toBuilder().name(null).build();

        mvc.perform(post("/api/v1/teams").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teamDtoWithoutName)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testPartialUpdateTeam() throws Exception {
        final var originalTeam = teamRepository.save(TestUtils.createMockTeam());
        final var teamDto = TeamDto.builder()
                .name("new name")
                .build();

        mvc.perform(patch("/api/v1/teams/{teamId}", originalTeam.getId()).contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teamDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(originalTeam.getId().toString()))
                .andExpect(jsonPath("$.name").value(teamDto.getName()));
    }

}
