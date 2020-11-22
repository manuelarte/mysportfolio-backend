package org.manuel.mysportfolio.userpermissions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.model.userrestrictions.UserRestriction;

class IntervalUtilsTest {

  @Test
  public void testFromBeginningOfYearFromReference() {
    final var userRestriction = new UserRestriction(3, Period.ofYears(1));
    final var reference = ZonedDateTime.of(LocalDate.of(2020, Month.FEBRUARY, 8).atTime(12, 0), ZoneId.of("UTC"));
    final var interval = IntervalUtils.fromBeginningOfYearFromReference().apply(userRestriction, reference.toInstant());
    assertEquals(LocalDate.of(2020, Month.JANUARY, 1).atStartOfDay(),
        LocalDateTime.ofInstant(interval.getStart(), ZoneOffset.UTC));
  }

  @Test
  public void testFromBeginningOfWeekFromReference() {
    final var userRestriction = new UserRestriction(3, Period.ofWeeks(1));
    final var reference = ZonedDateTime.of(LocalDate.of(2020, Month.FEBRUARY, 8).atTime(12, 0), ZoneId.of("UTC"));
    final var interval = IntervalUtils.fromBeginningOfWeekFromReference().apply(userRestriction, reference.toInstant());
    assertEquals(LocalDate.of(2020, Month.FEBRUARY, 2).atStartOfDay(),
        LocalDateTime.ofInstant(interval.getStart(), ZoneOffset.UTC));
  }

}
