package org.manuel.mysportfolio.model.entities.match.events;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Collections;
import javax.validation.Validation;
import javax.validation.Validator;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.model.entities.TeamOption;

class GoalMatchEventTest {

  private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory()
      .getValidator();

  @Test
  public void testRateLowerThanZero() {
    final var event = createGoalEvent("12345", -1d);
    final var violations = VALIDATOR.validate(event);
    assertFalse(violations.isEmpty());
  }

  @Test
  public void testRateGreaterThanFive() {
    final var event = createGoalEvent("12345", 6d);
    final var violations = VALIDATOR.validate(event);
    assertFalse(violations.isEmpty());
  }

  @Test
  public void testRateWithOneDigit() {
    final var event = createGoalEvent("12345", 1.2d);
    final var violations = VALIDATOR.validate(event);
    assertTrue(violations.isEmpty());
  }

  @Test
  public void testRateWithTwoDigits() {
    final var event = createGoalEvent("12345", 1.25d);
    final var violations = VALIDATOR.validate(event);
    assertFalse(violations.isEmpty());
  }

  private GoalMatchEvent createGoalEvent(final String playerId, final double rate) {
    return new GoalMatchEvent(new ObjectId(),
        TeamOption.HOME_TEAM, playerId, 30, null, null,
        Collections.singletonMap(playerId, new BigDecimal(String.valueOf(rate))),
        "A very nice volley", null);
  }

}
