package org.manuel.mysportfolio.model.entities.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalMatchEvent implements MatchEvent {

    @Id
    private ObjectId id;

    private Integer minute;

    private String description;

}
