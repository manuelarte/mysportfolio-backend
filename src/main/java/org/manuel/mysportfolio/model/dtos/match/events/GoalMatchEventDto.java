package org.manuel.mysportfolio.model.dtos.match.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.mysportfolio.model.TeamOption;
import io.github.manuelarte.mysportfolio.model.documents.match.events.BodyPart;
import io.github.manuelarte.mysportfolio.model.documents.match.events.GoalMatchEvent;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.bson.types.ObjectId;
import org.springframework.data.geo.Point;

@JsonDeserialize(builder = GoalMatchEventDto.GoalMatchEventDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Immutable
@lombok.Data
@lombok.AllArgsConstructor
@lombok.Builder(toBuilder = true)
public class GoalMatchEventDto implements MatchEventDto<GoalMatchEvent> {

  private final ObjectId id;

  @NotNull
  private final TeamOption team;

  private final String playerId;

  private final Integer minute;

  private final Point goalCoordinates;

  private final BodyPart bodyPart;

  /**
   * Rate of the goal depending on the player.
   */
  private final Map<@NotNull String,
      @Digits(integer = 1, fraction = 1)
      @Min(0)
      @Max(5)
      @NotNull BigDecimal> rates;

  @Size(max = 200)
  private final String description;

  private final AssistDetailsDto assist;

  @Override
  public GoalMatchEvent toMatchEvent() {
    return new GoalMatchEvent(id, team, playerId,
        minute, goalCoordinates, bodyPart, rates, description,
        Optional.ofNullable(assist).map(AssistDetailsDto::toAssistDetails).orElse(null));
  }

  @AssertTrue(message = "The goal coordinates aren't valid")
  @SuppressWarnings("unused")
  private boolean validGoalCoordinates() {
    if (goalCoordinates != null) {
      return goalCoordinates.getX() <= 0.5 && goalCoordinates.getX() >= -0.5
          && goalCoordinates.getY() <= 0.5 && goalCoordinates.getY() >= -0.5;
    }
    return true;
  }

  @AssertFalse(message = "The scorer and assister can't be the same person")
  @SuppressWarnings("unused")
  private boolean assistAndGoalTheSamePerson() {
    if (assist != null && playerId != null) {
      return playerId.equals(assist.getWho());
    }
    return false;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class GoalMatchEventDtoBuilder {

  }

}
