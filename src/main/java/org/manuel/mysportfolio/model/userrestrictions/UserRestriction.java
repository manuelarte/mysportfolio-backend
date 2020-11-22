package org.manuel.mysportfolio.model.userrestrictions;

import java.time.Period;

/**
 * Check that the user doesn't store more items than the max number in the time duration.
 */
@lombok.AllArgsConstructor
@lombok.Data
public class UserRestriction {

  private final int maxNumber;
  private final Period in;

}
