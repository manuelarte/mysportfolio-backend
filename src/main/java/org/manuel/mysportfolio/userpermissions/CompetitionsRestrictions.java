package org.manuel.mysportfolio.userpermissions;

import java.time.Period;
import java.util.Collections;
import java.util.Set;
import org.manuel.mysportfolio.model.userrestrictions.UserRestriction;

@lombok.experimental.UtilityClass
public class CompetitionsRestrictions {

  public static Set<UserRestrictionInterval> getFreeMembershipRestrictions() {
    final var maxNewCompetitionsPerYear = getOneYearFromBeginningOfYear(1);
    return Collections.singleton(maxNewCompetitionsPerYear);
  }

  public static Set<UserRestrictionInterval> getNoobMembershipRestrictions() {
    final var maxNewCompetitionsPerYear = getOneYearFromBeginningOfYear(2);
    return Collections.singleton(maxNewCompetitionsPerYear);
  }

  public static Set<UserRestrictionInterval> getAdvanceMembershipRestrictions() {
    final var maxNewCompetitionsPerYear = getOneYearFromBeginningOfYear(3);
    return Collections.singleton(maxNewCompetitionsPerYear);
  }

  public static Set<UserRestrictionInterval> getPremiumMembershipRestrictions() {
    final var maxNewCompetitionsPerYear = getOneYearFromBeginningOfYear(4);
    return Collections.singleton(maxNewCompetitionsPerYear);
  }

  private static UserRestriction getOneYearRestriction(final int maxNumber) {
    return new UserRestriction(maxNumber, Period.ofYears(1));
  }

  private static UserRestrictionInterval getOneYearFromBeginningOfYear(final int maxNumber) {
    return new UserRestrictionInterval(getOneYearRestriction(maxNumber), IntervalUtils.fromBeginningOfYearFromReference());
  }

}
