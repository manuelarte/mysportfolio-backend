package org.manuel.mysportfolio.userpermissions;

import java.time.Period;
import java.util.Collections;
import java.util.Set;
import org.manuel.mysportfolio.model.userrestrictions.UserRestriction;

@lombok.experimental.UtilityClass
public class TeamsRestrictions {

  public static Set<UserRestrictionInterval> getFreeMembershipRestrictions() {
    final var maxNewTeamsPerYear = getOneYearFromBeginningOfYear(2);
    return Collections.singleton(maxNewTeamsPerYear);
  }

  public static Set<UserRestrictionInterval> getNoobMembershipRestrictions() {
    final var maxNewTeamsPerYear = getOneYearFromBeginningOfYear(3);
    return Collections.singleton(maxNewTeamsPerYear);
  }

  public static Set<UserRestrictionInterval> getAdvanceMembershipRestrictions() {
    final var maxNewTeamsPerYear = getOneYearFromBeginningOfYear(4);
    return Collections.singleton(maxNewTeamsPerYear);
  }

  public static Set<UserRestrictionInterval> getPremiumMembershipRestrictions() {
    final var maxNewTeamsPerYear = getOneYearFromBeginningOfYear(10);
    return Collections.singleton(maxNewTeamsPerYear);
  }

  private static UserRestriction getOneYearRestriction(final int maxNumber) {
    return new UserRestriction(maxNumber, Period.ofYears(1));
  }

  private static UserRestrictionInterval getOneYearFromBeginningOfYear(final int maxNumber) {
    return new UserRestrictionInterval(getOneYearRestriction(maxNumber), IntervalUtils.fromBeginningOfYearFromReference());
  }

}
