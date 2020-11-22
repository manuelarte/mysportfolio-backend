package org.manuel.mysportfolio.userpermissions;

import java.time.Period;
import java.util.Collections;
import java.util.Set;
import org.manuel.mysportfolio.model.userrestrictions.UserRestriction;

@lombok.experimental.UtilityClass
public class MatchRestrictions {

  public static Set<UserRestrictionInterval> getFreeMembershipRestrictions() {
    final var maxNewMatchesPerWeek = getOneWeekFromBeginningOfWeek(1);
    return Collections.singleton(maxNewMatchesPerWeek);
  }

  public static Set<UserRestrictionInterval> getNoobMembershipRestrictions() {
    final var maxNewMatchesPerWeek = getOneWeekFromBeginningOfWeek(2);
    return Collections.singleton(maxNewMatchesPerWeek);
  }

  public static Set<UserRestrictionInterval> getAdvanceMembershipRestrictions() {
    final var maxNewMatchesPerWeek = getOneWeekFromBeginningOfWeek(3);
    return Collections.singleton(maxNewMatchesPerWeek);
  }

  public static Set<UserRestrictionInterval> getPremiumMembershipRestrictions() {
    final var maxNewMatchesPerWeek = getOneWeekFromBeginningOfWeek(4);
    return Collections.singleton(maxNewMatchesPerWeek);
  }

  private static UserRestriction getOneWeekRestriction(final int maxNumber) {
    return new UserRestriction(maxNumber, Period.ofDays(7));
  }

  private static UserRestrictionInterval getOneWeekFromBeginningOfWeek(final int maxNumber) {
    return new UserRestrictionInterval(getOneWeekRestriction(maxNumber), IntervalUtils.fromBeginningOfWeekFromReference());
  }

}
