package org.manuel.mysportfolio.model.entities.match.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalMatchEvent implements MatchEvent {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    @NotNull
    private TeamOption team;

    private String playerId;

    private Integer minute;

    private Point goalCoordinates;

    private BodyPart bodyPart;

    private String description;

    @AssertTrue
    private boolean validGoalCoordinates() {
        if (goalCoordinates != null) {
            return goalCoordinates.getX() <= 0.5 && goalCoordinates.getX() >= -0.5 &&
                    goalCoordinates.getY() <= 0.5 && goalCoordinates.getY() >= -0.5;
        }
        return true;
    }

}
