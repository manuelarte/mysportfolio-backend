package org.manuel.mysportfolio.controllers.command;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.manuelarte.mysportfolio.model.dtos.CompetitionDto;
import java.time.YearMonth;
import javax.inject.Inject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.ItConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.repositories.CompetitionRepository;
import org.manuel.mysportfolio.transformers.CompetitionToCompetitionDtoTransformer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Import(ItConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CompetitionCommandControllerTest {

  @Inject
  private ObjectMapper objectMapper;

  @Inject
  private CompetitionRepository competitionRepository;

  @Inject
  private CompetitionToCompetitionDtoTransformer competitionToCompetitionDtoTransformer;

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
        .andExpect(
            jsonPath("$.defaultMatchDay").value(competitionDto.getDefaultMatchDay().toString()));
  }

  @Test
  public void testSaveCompetitionFromToInvalid() throws Exception {
    final var competitionDto = TestUtils.createMockCompetitionDto().toBuilder()
        .from(YearMonth.now())
        .to(YearMonth.now().minusYears(1))
        .build();

    mvc.perform(post("/api/v1/competitions/").contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON)
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(competitionDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testSaveCompetitionNoNameGiven() throws Exception {
    final var competitionDtoWithoutName = TestUtils.createMockCompetitionDto().toBuilder()
        .name(null).build();

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

    mvc.perform(patch("/api/v1/competitions/{competitionId}", originalCompetition.getId())
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(competitionDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(originalCompetition.getId().toString()))
        .andExpect(jsonPath("$.name").value(competitionDto.getName()))
        .andExpect(jsonPath("$.sport").value(originalCompetition.getSport().toString()))
        .andExpect(jsonPath("$.defaultMatchDay")
            .value(originalCompetition.getDefaultMatchDay().toString()));
  }

  @Test
  public void testUpdateCompetitionValidChange() throws Exception {
    final var originalCompetition = competitionRepository.save(TestUtils.createMockCompetition());
    final YearMonth from = YearMonth.now();
    final var competitionDto = competitionToCompetitionDtoTransformer.apply(originalCompetition)
        .toBuilder()
        .from(from)
        .createdBy(null)
        .createdDate(null)
        .build();

    mvc.perform(put("/api/v1/competitions/{competitionId}", originalCompetition.getId())
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(competitionDto)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.from").value(from.toString()));
  }

  @Test
  public void testUpdateCompetitionCreatedByChanged() throws Exception {
    final var originalCompetition = competitionRepository.save(TestUtils.createMockCompetition());
    final var competitionDto = competitionToCompetitionDtoTransformer.apply(originalCompetition)
        .toBuilder()
        .createdBy("NOT VALID");

    mvc.perform(put("/api/v1/competitions/{competitionId}", originalCompetition.getId())
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(competitionDto)))
        .andExpect(status().is4xxClientError());
  }

}
