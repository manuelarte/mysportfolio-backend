package org.manuel.mysportfolio.model.entities.user;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.function.Predicate;
import org.manuel.mysportfolio.repositories.CompetitionRepository;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;

public enum AppMembership {

  FREE(2, 2, 2),
  NOOB(3, 3, 3), ADVANCE(4, 4, 3),
  PREMIUM(10, 10, 4);

  private final int maxNumberOfTeams;
  private final int maxNumberOfCompetitions;
  private final int maxNumberOfMatchesInOneWeek;

  AppMembership(final int maxNumberOfTeams, final int maxNumberOfCompetitions,
      final int maxNumberOfMatchesInOneWeek) {
    this.maxNumberOfTeams = maxNumberOfTeams;
    this.maxNumberOfCompetitions = maxNumberOfCompetitions;
    this.maxNumberOfMatchesInOneWeek = maxNumberOfMatchesInOneWeek;
  }

  public Predicate<OAuth2User> canSaveTeam(final TeamRepository teamRepository) {
    return oAuth2User -> {
      final var externalUserId = oAuth2User.getAttributes().get("sub").toString();
      return teamRepository.countAllByCreatedBy(externalUserId) < maxNumberOfTeams;
    };
  }

  public Predicate<OAuth2User> canSaveCompetition(
      final CompetitionRepository competitionRepository) {
    return oAuth2User -> {
      final var externalUserId = oAuth2User.getAttributes().get("sub").toString();
      return competitionRepository.countAllByCreatedBy(externalUserId) < maxNumberOfCompetitions;
    };
  }

  public Predicate<OAuth2User> canSaveMatch(final MatchRepository matchRepository,
      final LocalDate localDate) {
    return oAuth2User -> {
      final var externalUserId = oAuth2User.getAttributes().get("sub").toString();
      final DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
      final DayOfWeek lastDayOfWeek = firstDayOfWeek.minus(1);
      final LocalDate from = localDate.with(TemporalAdjusters.previous(firstDayOfWeek));
      final LocalDate to = localDate.with(TemporalAdjusters.nextOrSame(lastDayOfWeek));

      return matchRepository.countAllByCreatedDateBetweenAndCreatedBy(from, to, externalUserId)
          < maxNumberOfMatchesInOneWeek;
    };
  }

}
