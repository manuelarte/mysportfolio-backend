package org.manuel.mysportfolio.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import org.manuel.mysportfolio.badges.BadgeUtilHandler;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.Performance;
import org.manuel.mysportfolio.model.entities.match.PlayersPerformance;
import org.manuel.mysportfolio.model.entities.match.events.AssistDetails;
import org.manuel.mysportfolio.model.entities.match.events.GoalMatchEvent;
import org.manuel.mysportfolio.model.events.CompetitionCreatedEvent;
import org.manuel.mysportfolio.model.events.MatchCreatedEvent;
import org.manuel.mysportfolio.model.events.PlayersPerformanceUpdatedEvent;
import org.manuel.mysportfolio.model.events.TeamCreatedEvent;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.springframework.context.ApplicationEvent;

public enum Badge {

  FOOTBALL_FIRST_MATCH(100, "https://drive.google.com/uc?id=1N29Dwi4dKVhy11TLUjcXB0nhOHXGixIl",
      instanceOf(MatchCreatedEvent.class, ofSport(Sport.FOOTBALL))),

  FOOTBALL_FIRST_MATCH_WON(50, "https://drive.google.com/uc?id=1AJATBwdfaGNKjnqObGItWRw-Tbay0pOe",
      instanceOf(MatchCreatedEvent.class,
          Badge.ofSport(Sport.FOOTBALL).and(matchWon()))),

  FOOTBALL_FIRST_GOAL(50, "https://drive.google.com/uc?id=1CwutQso_fdHgAz3swluW-1lNfJmSW32A",
      instanceOf(MatchCreatedEvent.class,
          Badge.ofSport(Sport.FOOTBALL).and(isUserGoal(1)))),

  FOOTBALL_FIRST_HATTRICK(200, "https://drive.google.com/uc?id=1WuRTDzrM-tPMyht9bSQ-_17G6h3p8Nus",
      instanceOf(MatchCreatedEvent.class,
          Badge.ofSport(Sport.FOOTBALL).and(isUserGoal(3)))),

  FOOTBALL_FIRST_FIVE_STARS_GOAL(100,
      "https://drive.google.com/uc?id=1NDl5LrFHNPuVDg1K-HPVG5y3G9kEJA_j",
      instanceOf(MatchCreatedEvent.class,
          Badge.ofSport(Sport.FOOTBALL).and(isUserGoalAndRateIs(new BigDecimal(5))))),

  FOOTBALL_FIRST_ASSIST(20, "https://drive.google.com/uc?id=1gaxh3yh932NMEh86QiGHPWKQI-wl0lLA",
      instanceOf(MatchCreatedEvent.class,
          Badge.ofSport(Sport.FOOTBALL).and(isAssist(1)))),

  FOOTBALL_TEN_STAR_PERFORMANCE(100,
      "https://drive.google.com/uc?id=1i_z-bsD3pn8iMCfulYoyYmF0DFRqTrrK",
      instanceOf(PlayersPerformanceUpdatedEvent.class,
          Badge.<PlayersPerformance>matchMatches((b, match) -> b.getSport(match) == Sport.FOOTBALL)
              .and(Badge.isPerformanceRate(new BigDecimal("10"))))),

  FUTSAL_FIRST_MATCH(100, "https://drive.google.com/uc?id=18HuWT_FSCG0RSmmA5PD4t38mimUMUBWm",
      instanceOf(MatchCreatedEvent.class, ofSport(Sport.FUTSAL))),

  FUTSAL_FIRST_MATCH_WON(50, "https://drive.google.com/uc?id=1GVuxzxikDoBoynT5Df2PFmDL9sQ2GLli",
      instanceOf(MatchCreatedEvent.class,
          Badge.ofSport(Sport.FUTSAL).and(matchWon()))),

  FUTSAL_FIRST_GOAL(50, "https://drive.google.com/uc?id=1lnQiHawCi1tGye0wwB-Ie7XlG9caDuGb",
      instanceOf(MatchCreatedEvent.class,
          Badge.ofSport(Sport.FUTSAL).and(isUserGoal(1)))),

  FUTSAL_FIRST_HATTRICK(200, "https://drive.google.com/uc?id=1U7qV0HZYAn_--kriwIznev6_cA8vx-tK",
      instanceOf(MatchCreatedEvent.class,
          Badge.ofSport(Sport.FUTSAL).and(isUserGoal(3)))),

  FUTSAL_FIRST_FIVE_STARS_GOAL(100,
      "https://drive.google.com/uc?id=1MOszlS9h3sz6m2cFkHfKobrywaEfOyaS",
      instanceOf(MatchCreatedEvent.class,
          Badge.<Match<?, ?>>ofSport(Sport.FUTSAL).and(isUserGoalAndRateIs(new BigDecimal(5))))),

  FUTSAL_FIRST_ASSIST(20, "https://drive.google.com/uc?id=1F8nd0BGGUn3xnWoRQwfR_UK5AhIrjLqY",
      instanceOf(MatchCreatedEvent.class,
          Badge.<Match<?, ?>>ofSport(Sport.FUTSAL).and(isAssist(1)))),

  FUTSAL_TEN_STAR_PERFORMANCE(100,
      "https://drive.google.com/uc?id=1FOtOgCrRQ1tNplx8H194uvrsBLolnDdn",
      instanceOf(PlayersPerformanceUpdatedEvent.class,
          Badge.<PlayersPerformance>matchMatches((b, match) -> b.getSport(match) == Sport.FUTSAL)
              .and(Badge.isPerformanceRate(new BigDecimal("10"))))),

  TEAM_FIRST_ADDED(100, "https://drive.google.com/uc?id=1_E1wjcUOJZq13wp_43-49M3EpZ79PeUl",
      instanceOf(TeamCreatedEvent.class)),

  COMPETITION_FOOTBALL_FIRST_ADDED(50,
      "https://drive.google.com/uc?id=1pXMg-DB5nZq4xS9bZ4iifHZ8lgki_rAj",
      instanceOf(CompetitionCreatedEvent.class, isSport(Sport.FOOTBALL))),

  COMPETITION_FUTSAL_FIRST_ADDED(50,
      "https://drive.google.com/uc?id=1e27MgxXvelL7mxzZEKf87oYEQ_6kYCyr",
      instanceOf(CompetitionCreatedEvent.class, isSport(Sport.FUTSAL)));

  @lombok.Getter
  private final int points;

  @lombok.Getter
  private final String imageUrl;

  @lombok.Getter
  private final BiPredicate<BadgeUtilHandler, Object> predicate;

  Badge(final int points, final String imageUrl,
      final BiPredicate<BadgeUtilHandler, Object> predicate) {
    this.points = points;
    this.imageUrl = imageUrl;
    this.predicate = predicate;
  }

  private static <T extends ApplicationEvent, Y> BiPredicate<BadgeUtilHandler, Object> instanceOf(
      final Class<T> clazz,
      final BiPredicate<BadgeUtilHandler, Y> filter) {
    return (badgeUtilHandler, it) -> clazz.isInstance(it) && filter
        .test(badgeUtilHandler, (Y) ((T) it).getSource());
  }

  private static <T extends ApplicationEvent> BiPredicate<BadgeUtilHandler, Object> instanceOf(
      final Class<T> clazz) {
    return instanceOf(clazz, (x, y) -> true);
  }

  private static BiPredicate<BadgeUtilHandler, SportDependent> isSport(
      final Sport sport) {
    return (badgeUtilHandler, m) -> m.getSport().equals(sport);
  }

  private static BiPredicate<BadgeUtilHandler, Match<?, ?>> ofSport(
      final Sport sport) {
    return (badgeUtilHandler, m) -> badgeUtilHandler.getSport(m).equals(sport);
  }

  private static BiPredicate<BadgeUtilHandler, Match<?, ?>> matchWon() {
    return (badgeUtilHandler, match) -> {
      final var userId = badgeUtilHandler.getUserId();
      final var option = match.getPlayedFor().get(userId);
      return (option == TeamOption.HOME_TEAM && match.getGoals(TeamOption.HOME_TEAM) > match
          .getGoals(TeamOption.AWAY_TEAM))
          || (option == TeamOption.AWAY_TEAM && match.getGoals(TeamOption.HOME_TEAM) < match
          .getGoals(TeamOption.AWAY_TEAM));
    };
  }

  private static BiPredicate<BadgeUtilHandler, Match<?, ?>> isUserGoal(int min) {
    return (badgeUtilHandler, match) -> {
      final var userId = badgeUtilHandler.getUserId();
      final var teamOption = match.getPlayedFor().get(userId);
      return goalMatchEventStream(match)
          .filter(it -> it.getTeam().equals(teamOption))
          .filter(it -> userId.equals(it.getPlayerId())).count() >= min;
    };
  }

  private static BiPredicate<BadgeUtilHandler, Match<?, ?>> isUserGoalAndRateIs(BigDecimal rate) {
    return (badgeUtilHandler, match) -> {
      final var userId = badgeUtilHandler.getUserId();
      final var teamOption = match.getPlayedFor().get(userId);
      return goalMatchEventStream(match)
          .filter(it -> it.getTeam().equals(teamOption))
          .filter(it -> userId.equals(it.getPlayerId()))
          .flatMap(it -> it.getRates().values().stream()).anyMatch(it -> it.compareTo(rate) == 0);
    };
  }

  private static BiPredicate<BadgeUtilHandler, Match<?, ?>> isAssist(int min) {
    return (badgeUtilHandler, match) -> {
      final var userId = badgeUtilHandler.getUserId();
      final var teamOption = match.getPlayedFor().get(userId);
      return goalMatchEventStream(match)
          .filter(it -> it.getTeam().equals(teamOption))
          .filter(it -> userId.equals(Optional.ofNullable(it.getAssist()).map(
              AssistDetails::getWho).orElse(null))).count() >= min;
    };
  }

  private static Stream<GoalMatchEvent> goalMatchEventStream(final Match<?, ?> match) {
    return Optional.ofNullable(match.getEvents()).orElseGet(ArrayList::new).stream()
        .filter(GoalMatchEvent.class::isInstance)
        .map(GoalMatchEvent.class::cast);
  }

  private static <T extends MatchDependent> BiPredicate<BadgeUtilHandler, T> matchMatches(
      final BiPredicate<BadgeUtilHandler, Match<?, ?>> predicate) {
    return (badgeUtilHandler, t) -> badgeUtilHandler.getRepository(MatchRepository.class)
        .flatMap(it -> it.findById(t.getMatchId()))
        .map(m -> predicate.test(badgeUtilHandler, m)).orElse(false);
  }

  private static BiPredicate<BadgeUtilHandler, PlayersPerformance> isPerformanceRate(
      BigDecimal rateExpected) {
    return (badgeUtilHandler, playersPerformance) -> {
      final var userId = badgeUtilHandler.getUserId();
      return playersPerformance.getPerformance(userId).map(Performance::getRate)
          .map(r -> r.equals(rateExpected)).orElse(false);
    };
  }

}
