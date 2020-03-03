package org.manuel.mysportfolio.transformers.playerprofile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileSportSeasonSummaryDto;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.events.AssistDetails;
import org.manuel.mysportfolio.model.entities.match.events.GoalMatchEvent;
import org.manuel.mysportfolio.services.query.MatchQueryService;

@lombok.AllArgsConstructor
public class PlayerProfileSportSeasonSummaryDtoTransformer implements
    Function<Sport, PlayerProfileSportSeasonSummaryDto> {

  private final String externalId;
  private final Year year;
  private final MatchQueryService matchQueryService;

  @Override
  public PlayerProfileSportSeasonSummaryDto apply(final Sport sport) {
    final var from = year.atDay(1);
    final var to = !year.isLeap() ? year.atDay(365) : year.atDay(366);
    final var matches = matchQueryService
        .findAllByPlayedForContainsAndStartDateIsBetweenAndSportIs(externalId, from, to, sport);
    final var userGoals = getGoalMatchEventOfUser(externalId, matches);
    final var assistCount = getNumberOfAssists(externalId, matches);
    final var summary = PlayerProfileSportSeasonSummaryDto.builder()
        .numberOfMatchesPlayed(matches.size())
        .numberOfGoals(userGoals.size())
        .averageGoalRate(getAverageGoalRate(userGoals))
        .numberOfAssists((int) assistCount)
        .build();
    return summary;
  }

  private Set<GoalMatchEvent> getGoalMatchEventOfUser(final String externalId,
      final Collection<Match<?, ?>> matches) {
    return matches.stream()
        .flatMap(it -> Optional.ofNullable(it.getEvents()).orElse(Collections.emptyList()).stream())
        .filter(event -> event instanceof GoalMatchEvent).map(event -> (GoalMatchEvent) event)
        .filter(goalEvent -> externalId.equals(goalEvent.getPlayerId()))
        .collect(Collectors.toSet());
  }

  private BigDecimal getAverageGoalRate(final Set<GoalMatchEvent> goals) {
    OptionalDouble average = goals.stream()
        .filter(it -> it.getRates() != null && !it.getRates().isEmpty())
        .map(it -> getAverageGoalRate(it)).mapToDouble(it -> it.doubleValue()).average();
    return average.isPresent() ? new BigDecimal(average.getAsDouble())
        .setScale(1, RoundingMode.HALF_EVEN) : null;
  }

  private BigDecimal getAverageGoalRate(final GoalMatchEvent goal) {
    double average = Optional.ofNullable(goal.getRates()).orElse(Collections.emptyMap())
        .values().stream()
        .mapToDouble(BigDecimal::doubleValue).average().getAsDouble();
    return new BigDecimal(average).setScale(1, RoundingMode.HALF_EVEN);
  }

  private long getNumberOfAssists(final String externalId, final Collection<Match<?, ?>> matches) {
    return matches.stream()
        .flatMap(it -> Optional.ofNullable(it.getEvents()).orElse(Collections.emptyList()).stream())
        .filter(event -> event instanceof GoalMatchEvent).map(event -> (GoalMatchEvent) event)
        .filter(goalEvent -> externalId.equals(
            Optional.ofNullable(goalEvent.getAssist()).orElse(new AssistDetails()).getWho()))
        .count();
  }
}
