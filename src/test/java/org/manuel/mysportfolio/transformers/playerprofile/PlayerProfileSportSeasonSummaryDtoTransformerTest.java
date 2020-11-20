package org.manuel.mysportfolio.transformers.playerprofile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.manuelarte.mysportfolio.model.Sport;
import io.github.manuelarte.mysportfolio.model.TeamOption;
import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import io.github.manuelarte.mysportfolio.model.documents.match.events.GoalMatchEvent;
import io.github.manuelarte.mysportfolio.model.documents.match.events.MatchEvent;
import io.github.manuelarte.mysportfolio.model.documents.match.type.FriendlyMatchType;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileSportSeasonSummaryDto;
import org.manuel.mysportfolio.services.query.MatchQueryService;

class PlayerProfileSportSeasonSummaryDtoTransformerTest {

  @Test
  @DisplayName("Summary creation football with one match and no goals")
  public void testSummaryCreationForFootballOneMatchNoGoals() {
    final var userId = "userId";
    final var year = Year.now();
    final var matchQueryService = mock(MatchQueryService.class);
    when(matchQueryService
        .findAllByPlayedForContainsAndStartDateIsBetweenAndSportIs(eq(userId), any(), any(),
            eq(Sport.FOOTBALL)))
        .thenReturn(Collections.singletonList(
            createMatch(userId, Instant.now(), Sport.FOOTBALL, Collections.emptyList())));
    final var expected = PlayerProfileSportSeasonSummaryDto.builder()
        .numberOfMatchesPlayed(1)
        .numberOfGoals(0)
        .averageGoalRate(null)
        .goalRatio(BigDecimal.ZERO.setScale(2))
        .numberOfAssists(0)
        .build();

    final var transformer = new PlayerProfileSportSeasonSummaryDtoTransformer(userId, year,
        matchQueryService);
    final var actual = transformer.apply(Sport.FOOTBALL);
    assertEquals(actual, expected);
  }

  @Test
  @DisplayName("Summary creation football with one match and one goal")
  public void testSummaryCreationForFootballOneMatchOneGoal() {
    final var userId = "userId";
    final var year = Year.now();
    final List<Double> goalsRate = Collections.singletonList(3.5d);
    final var matchQueryService = mock(MatchQueryService.class);
    when(matchQueryService
        .findAllByPlayedForContainsAndStartDateIsBetweenAndSportIs(eq(userId), any(), any(),
            eq(Sport.FOOTBALL)))
        .thenReturn(Collections
            .singletonList(createMatch(userId, Instant.now(), Sport.FOOTBALL, goalsRate)));
    final var expected = PlayerProfileSportSeasonSummaryDto.builder()
        .numberOfMatchesPlayed(1)
        .numberOfGoals(goalsRate.size())
        .averageGoalRate(new BigDecimal("3.5"))
        .goalRatio(BigDecimal.ONE.setScale(2))
        .numberOfAssists(0)
        .build();

    final var transformer = new PlayerProfileSportSeasonSummaryDtoTransformer(userId, year,
        matchQueryService);
    final var actual = transformer.apply(Sport.FOOTBALL);
    assertEquals(actual, expected);
  }

  @Test
  @DisplayName("Summary creation football with one match and two goal")
  public void testSummaryCreationForFootballOneMatchTwoGoals() {
    final var userId = "userId";
    final var year = Year.now();
    final List<Double> goalsRate = List.of(3.5d, 0.5d);
    final var matchQueryService = mock(MatchQueryService.class);
    when(matchQueryService
        .findAllByPlayedForContainsAndStartDateIsBetweenAndSportIs(eq(userId), any(), any(),
            eq(Sport.FOOTBALL)))
        .thenReturn(Collections
            .singletonList(createMatch(userId, Instant.now(), Sport.FOOTBALL, goalsRate)));
    final var expected = PlayerProfileSportSeasonSummaryDto.builder()
        .numberOfMatchesPlayed(1)
        .numberOfGoals(goalsRate.size())
        .averageGoalRate(new BigDecimal("2.0"))
        .goalRatio(new BigDecimal("2").setScale(2))
        .numberOfAssists(0)
        .build();

    final var transformer = new PlayerProfileSportSeasonSummaryDtoTransformer(userId, year,
        matchQueryService);
    final var actual = transformer.apply(Sport.FOOTBALL);
    assertEquals(actual, expected);
  }

  private Match<TeamType, TeamType> createMatch(final String userId, final Instant startDate,
      final Sport sport, List<Double> goalsRate) {
    final var match = new Match<>();
    match.setStartDate(startDate);
    match.setType(new FriendlyMatchType(sport));
    match.setPlayedFor(Collections.singletonMap(userId, TeamOption.HOME_TEAM));
    final List<MatchEvent> events = goalsRate.stream().map(i -> {
      final var goalEvent = new GoalMatchEvent();
      goalEvent.setPlayerId(userId);
      goalEvent.setRates(Collections.singletonMap(userId, new BigDecimal(i)));
      return goalEvent;
    }).collect(Collectors.toList());
    match.setEvents(events);
    return match;
  }

}