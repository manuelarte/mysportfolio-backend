package org.manuel.mysportfolio.model.entities.match.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class GoalMatchEvent implements TeamMatchEvent {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    @NotNull
    private TeamOption team;

    private String playerId;

    private Integer minute;

    private Point goalCoordinates;

    private BodyPart bodyPart;

    /**
     * Rate of the goal depending on the player
     */
    private Map<String, BigDecimal> rates;

    @Size(max = 200)
    private String description;

    private AssistDetails assist;

  @AssertTrue(message = "The goal coordinates aren't valid")
  @SuppressWarnings("unused")
    private boolean validGoalCoordinates() {
        if (goalCoordinates != null) {
            return goalCoordinates.getX() <= 0.5 && goalCoordinates.getX() >= -0.5 &&
                    goalCoordinates.getY() <= 0.5 && goalCoordinates.getY() >= -0.5;
        }
        return true;
    }

  @AssertFalse(message = "The scorer and assister can't be the same person")
  @SuppressWarnings("unused")
    private boolean assistAndGoalTheSamePerson() {
        if (assist != null && playerId != null) {
            return playerId.equals(assist);
        }
        return false;
    }

    @AssertFalse(message = "The rate values aren't valid")
    @SuppressWarnings("unused")
    private boolean isRatesValid() {
        return Optional.ofNullable(rates).orElse(Collections.emptyMap()).values()
            .stream().anyMatch(it -> it.compareTo(BigDecimal.ZERO) < 0
                && it.compareTo(new BigDecimal("5")) > 1 );
    }


}
