package org.manuel.mysportfolio.controllers.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.ITConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.dtos.CompetitionDto;
import org.manuel.mysportfolio.repositories.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(ITConfiguration.class)
@ExtendWith({SpringExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CompetitionCommandControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CompetitionRepository competitionRepository;

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
        competitionRepository.deleteAll();
    }

    @Test
    public void testSaveCompetitionAllFieldsValid() throws Exception {
        final var competitionDto = TestUtils.createMockCompetitionDto();

        mvc.perform(post("/api/v1/competitions/").contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(competitionDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.name").value(competitionDto.getName()))
                .andExpect(jsonPath("$.sport").value(competitionDto.getSport().toString()))
                .andExpect(jsonPath("$.defaultMatchDay").value(competitionDto.getDefaultMatchDay().toString()));
    }

    @Test
    public void testSaveCompetitionNoNameGiven() throws Exception {
        final var competitionDtoWithoutName = TestUtils.createMockCompetitionDto().toBuilder().name(null).build();

        mvc.perform(post("/api/v1/competitions").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(competitionDtoWithoutName)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testPartialUpdateCompetition() throws Exception {
        final var originalCompetition = competitionRepository.save(TestUtils.createMockCompetition());
        final var competitionDto = CompetitionDto.builder()
                .version(originalCompetition.getVersion())
                .name("new name")
                .build();

        mvc.perform(patch("/api/v1/competitions/{competitionId}", originalCompetition.getId()).contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(competitionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(originalCompetition.getId().toString()))
                .andExpect(jsonPath("$.name").value(competitionDto.getName()))
                .andExpect(jsonPath("$.sport").value(originalCompetition.getSport().toString()))
                .andExpect(jsonPath("$.defaultMatchDay").value(originalCompetition.getDefaultMatchDay().toString()));
    }

}
