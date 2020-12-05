package org.manuel.mysportfolio.transformers.playerprofile;

import static java.util.stream.Collectors.groupingBy;

import io.github.manuelarte.mysportfolio.model.Sport;
import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.TeamType;
import io.github.manuelarte.mysportfolio.model.documents.match.events.AssistDetails;
import io.github.manuelarte.mysportfolio.model.documents.match.events.GoalMatchEvent;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileTimeIntervalSummaryDto;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.manuel.mysportfolio.services.query.MatchQueryService;

@lombok.AllArgsConstructor
public class PlayerProfileSportSeasonSummaryDtoTransformer implements
    Function<Sport, Map<Month, PlayerProfileTimeIntervalSummaryDto>> {

  private final String externalId;
  private final Year year;
  private final MatchQueryService matchQueryService;

  @Override
  public Map<Month, PlayerProfileTimeIntervalSummaryDto> apply(final Sport sport) {
    final var from = year.atDay(1);
    final var to = !year.isLeap() ? year.atDay(365) : year.atDay(366);
    final Collection<Match<TeamType, TeamType>> matchesInYear = matchQueryService
        .findAllByPlayedForContainsAndStartDateIsBetweenAndSportIs(externalId, from, to, sport);

    final Map<Month, List<Match<TeamType, TeamType>>> groupByMonth = matchesInYear.stream()
        .collect(groupingBy(entry -> entry.getStartDate().atZone(ZoneId.of("UTC")).getMonth()));

    return groupByMonth.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> {
      final List<Match<TeamType, TeamType>> matches = e.getValue();
      final var userGoals = getGoalMatchEventOfUser(matches);
      final var assistCount = getNumberOfAssists(externalId, matches);
      return PlayerProfileTimeIntervalSummaryDto.builder()
          .numberOfMatchesPlayed(matches.size())
          .numberOfGoals(userGoals.size())
          .numberOfAssists((int) assistCount)
          .build();
    }));
  }

  private Set<GoalMatchEvent> getGoalMatchEventOfUser(final Collection<Match<TeamType, TeamType>> matches) {
    return matches.stream()
        .flatMap(it -> Optional.ofNullable(it.getEvents()).orElse(Collections.emptyList()).stream())
        .filter(event -> event instanceof GoalMatchEvent).map(event -> (GoalMatchEvent) event)
        .filter(goalEvent -> this.externalId.equals(goalEvent.getPlayerId()))
        .collect(Collectors.toSet());
  }

  private long getNumberOfAssists(final String externalId, final Collection<Match<TeamType, TeamType>> matches) {
    return matches.stream()
        .flatMap(it -> Optional.ofNullable(it.getEvents()).orElse(Collections.emptyList()).stream())
        .filter(event -> event instanceof GoalMatchEvent).map(event -> (GoalMatchEvent) event)
        .filter(goalEvent -> externalId.equals(
            Optional.ofNullable(goalEvent.getAssist()).orElse(new AssistDetails()).getWho()))
        .count();
  }
}
