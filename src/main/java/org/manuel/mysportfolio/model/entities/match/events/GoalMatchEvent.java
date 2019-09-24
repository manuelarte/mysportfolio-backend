package org.manuel.mysportfolio.model.entities.match.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalMatchEvent implements MatchEvent {

    public enum GoalTeam {
        HOME_TEAM, AWAY_TEAM
    }

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    @NotNull
    private GoalTeam team;

    private String playerId;

    private Integer minute;

    private Point goalCoordinates;

    private BodyPart bodyPart;

    private String description;

}
