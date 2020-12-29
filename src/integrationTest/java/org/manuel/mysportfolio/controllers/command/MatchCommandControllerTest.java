package org.manuel.mysportfolio.controllers.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.manuelarte.mysportfolio.model.TeamOption;
import io.github.manuelarte.mysportfolio.model.documents.match.AnonymousTeam;
import io.github.manuelarte.mysportfolio.model.dtos.match.MatchDto;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.ItConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.manuel.mysportfolio.transformers.match.MatchToMatchDtoTransformer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

@SpringBootTest
@Import(ItConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MatchCommandControllerTest {

  private static final String USER_ID = "123456789";

  @Inject
  private ObjectMapper objectMapper;
  @Inject
  private TeamRepository teamRepository;
  @Inject
  private MatchToMatchDtoTransformer matchToMatchDtoTransformer;
  @Inject
  private MatchRepository matchRepository;
  @Inject
  private WebApplicationContext context;

  private MockMvc mvc;

  @BeforeEach
  @SuppressWarnings("checkstyle:javadoctype")
  public void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  @AfterEach
  public void tearDown() {
    matchRepository.deleteAll();
    teamRepository.deleteAll();
  }

  @Test
  public void testSaveMatchWithOneTeamRegistered() throws Exception {
    try {
      SecurityContextHolder.getContext().setAuthentication(TestUtils.createAuthentication(ItConfiguration.IT_USER_ID));
      final var teamSaved = teamRepository.save(TestUtils.createMockTeam());
      final var matchDto = TestUtils
          .createMockMatchDto(TestUtils.createMockRegisteredTeamDto(teamSaved.getId()),
              TestUtils.createMockAnonymousTeamDto(), 1, 2,
              Collections.singletonMap("123456789", TeamOption.HOME_TEAM));

      mvc.perform(post("/api/v1/matches").contentType(APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(matchDto)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.id", Matchers.notNullValue()))
          .andExpect(jsonPath("$.type.type").value("friendly"))
          .andExpect(jsonPath("$.homeTeam.type").value("registered"))
          .andExpect(jsonPath("$.homeTeam.teamId").value(matchDto.getHomeTeam().getTeamId().toString()))
          .andExpect(jsonPath("$.awayTeam.type").value("anonymous"))
          .andExpect(jsonPath("$.awayTeam.name").value(matchDto.getAwayTeam().getName()));
    } finally {
      SecurityContextHolder.clearContext();
    }
  }

  @Test
  public void testSaveMatchWithTwoAnonymousTeams() throws Exception {
    final var matchDto = TestUtils.createMockMatchDto(TestUtils.createMockAnonymousTeamDto(),
        TestUtils.createMockAnonymousTeamDto(), 0, 0,
        Collections.singletonMap(USER_ID, TeamOption.HOME_TEAM));

    mvc.perform(post("/api/v1/matches").contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(matchDto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", Matchers.notNullValue()))
        .andExpect(jsonPath("$.type.type").value("friendly"))
        .andExpect(jsonPath("$.homeTeam.type").value("anonymous"))
        .andExpect(jsonPath("$.homeTeam.name").value(matchDto.getHomeTeam().getName()))
        .andExpect(jsonPath("$.awayTeam.type").value("anonymous"))
        .andExpect(jsonPath("$.awayTeam.name").value(matchDto.getAwayTeam().getName()))
        .andExpect(jsonPath("$.events[*].id", Matchers.notNullValue()));
  }

  @Test
  public void testUpdateMatchWithTwoAnonymousTeams() throws Exception {
    final var match = matchRepository.save(
        TestUtils.createMockMatch(TestUtils.createMockAnonymousTeam(),
            TestUtils.createMockAnonymousTeam(), USER_ID));
    final String description = "new description";
    final MatchDto<?, ?> updateDto = matchToMatchDtoTransformer.apply(match).toBuilder()
        .id(null).createdBy(null)
        .description(description)
        .build();

    mvc.perform(put("/api/v1/matches/{id}", match.getId()).contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", Matchers.notNullValue()))
        .andExpect(jsonPath("$.type.type").value("friendly"))
        .andExpect(jsonPath("$.homeTeam.type").value("anonymous"))
        .andExpect(jsonPath("$.homeTeam.name").value(((AnonymousTeam)match.getHomeTeam()).getName()))
        .andExpect(jsonPath("$.awayTeam.type").value("anonymous"))
        .andExpect(jsonPath("$.awayTeam.name").value(((AnonymousTeam)match.getAwayTeam()).getName()))
        .andExpect(jsonPath("$.description").value(description))
        .andExpect(jsonPath("$.events[*].id", Matchers.notNullValue()));
  }

  @Test
  public void testUpdateNotExistingMatch() {
    final var match = matchRepository.save(TestUtils.createMockMatch(TestUtils.createMockAnonymousTeam(),
            TestUtils.createMockAnonymousTeam(), USER_ID));
    final String description = "new description";
    final MatchDto<?, ?> updateDto = matchToMatchDtoTransformer.apply(match).toBuilder()
        .id(null).createdBy(null)
        .description(description)
        .build();

    final var e = assertThrows(NestedServletException.class,
        () -> mvc.perform(put("/api/v1/matches/{id}", new ObjectId()).contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateDto)))
        .andExpect(status().isBadRequest()));
    assertEquals(ConstraintViolationException.class, e.getCause().getClass());
  }

  @Test
  public void testSaveMatchWithChipTooBig() throws Exception {
    final var matchDto = TestUtils.createMockMatchDto(TestUtils.createMockAnonymousTeamDto(),
        TestUtils.createMockAnonymousTeamDto(), 0, 0,
        Collections.singletonMap(USER_ID, TeamOption.HOME_TEAM),
        RandomStringUtils.randomAlphabetic(22));

    mvc.perform(post("/api/v1/matches").contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(matchDto)))
        .andExpect(status().is4xxClientError());
  }

  @Test
  public void testSaveMatchWithTooManyChips() throws Exception {
    final Set<String> chips = IntStream.range(0, 6).mapToObj(it -> RandomStringUtils.random(5))
        .collect(Collectors.toSet());
    final var matchDto = TestUtils.createMockMatchDto(TestUtils.createMockAnonymousTeamDto(),
        TestUtils.createMockAnonymousTeamDto(), 0, 0,
        Collections.singletonMap(USER_ID, TeamOption.HOME_TEAM),
        chips.toArray(new String[]{}));

    mvc.perform(post("/api/v1/matches").contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(matchDto)))
        .andExpect(status().is4xxClientError());
  }

}
