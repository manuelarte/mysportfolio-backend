package org.manuel.mysportfolio.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalMatchEvent implements MatchEvent {

    @Id
    private UUID id;

    private Integer minute;

    private String description;

}
