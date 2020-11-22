package org.manuel.mysportfolio.userpermissions;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.function.BiFunction;
import org.manuel.mysportfolio.model.userrestrictions.UserRestriction;
import org.threeten.extra.Interval;

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
