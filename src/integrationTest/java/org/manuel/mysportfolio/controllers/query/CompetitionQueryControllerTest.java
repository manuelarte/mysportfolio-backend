package org.manuel.mysportfolio.controllers.query;

import static io.github.manuelarte.mysportfolio.model.Sport.FOOTBALL;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.manuelarte.mysportfolio.model.documents.competition.Competition;
import java.time.YearMonth;
import javax.inject.Inject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.ItConfiguration;
import org.manuel.mysportfolio.repositories.CompetitionRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(ItConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CompetitionQueryControllerTest {

  @Inject
  private CompetitionRepository competitionRepository;

  @Inject
  private MockMvc mvc;

  @AfterEach
  public void tearDown() {
    competitionRepository.deleteAll();
  }

  @Test
  public void testGetCompetitions() throws Exception {
    final var competitionSaved = competitionRepository.save(
        new Competition(null, null, "KNVB 4th Klass", FOOTBALL, MONDAY,
            null, null, null));

    mvc.perform(get("/api/v1/competitions").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize((int) competitionRepository.count())))
        .andExpect(jsonPath("$.content[0].name", Matchers.is(competitionSaved.getName())));
  }

  @Test
  public void testGetActiveCompetitions() throws Exception {
    final var competitionSaved = competitionRepository.save(
        new Competition(null, null, "KNVB 4th Klass 2019", FOOTBALL, MONDAY,
            YearMonth.of(2019, 1), YearMonth.of(2020, 1), null));

    final var competitionSaved2 = competitionRepository.save(
        new Competition(null, null, "KNVB 4e Klass 2020", FOOTBALL, SATURDAY,
            YearMonth.of(2020, 1), YearMonth.of(2021, 1), null));
    final var competitionSaved3 = competitionRepository.save(
        new Competition(null, null, "KNVB 1e Klass 2020", FOOTBALL, SATURDAY,
            YearMonth.of(2020, 1), null, null));

    mvc.perform(get("/api/v1/competitions?matchDate=2020-02-01").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(2)))
        .andExpect(jsonPath("$.content[0].name", Matchers.is(competitionSaved2.getName())))
        .andExpect(jsonPath("$.content[1].name", Matchers.is(competitionSaved3.getName())));
  }

}
