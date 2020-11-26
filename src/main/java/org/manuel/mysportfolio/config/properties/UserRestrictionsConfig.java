package org.manuel.mysportfolio.config.properties;

import io.github.manuelarte.mysportfolio.model.documents.user.AppMembership;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.threeten.extra.Interval;

@Configuration
@ConfigurationProperties("user")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class UserRestrictionsConfig {

  private Map<AppMembership, MembershipUserRestriction> restrictions;

  public MembershipUserRestriction of(final AppMembership appMembership) {
    return restrictions.get(appMembership);
  }

  @lombok.AllArgsConstructor
  @lombok.NoArgsConstructor
  @lombok.Data
  public static class MembershipUserRestriction {

    private Set<UserRestriction> teams;
    private Set<UserRestriction> competitions;
    private Set<UserRestriction> matches;

  }

  @lombok.AllArgsConstructor
  @lombok.NoArgsConstructor
  @lombok.Data
  public static class UserRestriction {

    private int maxNumber;
    private Period in;
    private InstantFunction function;

    public Interval getInterval(final Instant reference) {
      return function.getInterval().apply(this, reference);
    }

  }

  @lombok.AllArgsConstructor
  @lombok.Getter
  public enum InstantFunction {
    BEGINNING_YEAR(IntervalUtils.fromBeginningOfYearFromReference()), BEGINNING_WEEK(IntervalUtils.fromBeginningOfWeekFromReference());

    private BiFunction<UserRestriction, Instant, Interval> interval;
  }

  @lombok.experimental.UtilityClass
  class IntervalUtils {

    public static BiFunction<UserRestriction, Instant, Interval> fromBeginningOfYearFromReference() {
      return (userRestriction, reference) -> {
        final var year = Year.of(reference.atZone(ZoneOffset.UTC).getYear());
        // get 1st January of year in instant
        final var from = year.atDay(1).atStartOfDay().atZone(ZoneOffset.UTC);
        return Interval.of(from.toInstant(), from.plus(userRestriction.getIn()).toInstant());
      };
    }

    public static BiFunction<UserRestriction, Instant, Interval> fromBeginningOfWeekFromReference() {
      return (userRestriction, reference) -> {
        final DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        final DayOfWeek lastDayOfWeek = firstDayOfWeek.minus(1);
        final var localDate = LocalDate.ofInstant(reference, ZoneId.systemDefault());
        // get First day of that week
        final var from = localDate.with(TemporalAdjusters.previous(firstDayOfWeek))
            .atStartOfDay(ZoneId.of("UTC")).toInstant();
        // get Last day of that week
        final var to = ZonedDateTime.of(localDate.with(TemporalAdjusters.nextOrSame(lastDayOfWeek))
            .atStartOfDay().with(LocalTime.MAX), ZoneId.systemDefault()).toInstant();
        return Interval.of(from, from.plus(userRestriction.getIn()));
      };
    }

  }

}
