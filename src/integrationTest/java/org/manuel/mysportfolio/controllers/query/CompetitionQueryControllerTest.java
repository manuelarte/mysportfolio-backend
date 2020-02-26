package org.manuel.mysportfolio.controllers.query;

import javax.inject.Inject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.ITConfiguration;
import org.manuel.mysportfolio.model.entities.Competition;
import org.manuel.mysportfolio.repositories.CompetitionRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.time.DayOfWeek.MONDAY;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.manuel.mysportfolio.model.Sport.FOOTBALL;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(ITConfiguration.class)
@ExtendWith({SpringExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CompetitionQueryControllerTest {

    @Inject
    private CompetitionRepository competitionRepository;

    @Inject
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    public void tearDown() {
        competitionRepository.deleteAll();
    }

    @Test
    public void testGetCompetitions() throws Exception {
        final var competitionSaved = competitionRepository.save(
                new Competition(null, null, "KNVB 4th Klass", FOOTBALL, MONDAY, null));

        mvc.perform(get("/api/v1/competitions").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize((int)competitionRepository.count())))
                .andExpect(jsonPath("$.content[0].name", Matchers.is(competitionSaved.getName())));
    }

}
