package org.manuel.mysportfolio.userpermissions;

import java.time.Instant;
import java.util.function.BiFunction;
import org.manuel.mysportfolio.model.userrestrictions.UserRestriction;
import org.threeten.extra.Interval;

@lombok.AllArgsConstructor
@lombok.Data
public class UserRestrictionInterval {

  private final UserRestriction userRestriction;
  private final BiFunction<UserRestriction, Instant, Interval> function;

  public Interval getInterval(final Instant reference) {
    return function.apply(userRestriction, reference);
  }


}
