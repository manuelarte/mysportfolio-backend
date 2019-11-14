package org.manuel.mysportfolio.model.entities.match;

import org.manuel.mysportfolio.model.TeamInfo;
import org.manuel.mysportfolio.model.entities.team.TeamImage;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class AnonymousTeam implements TeamType, TeamInfo {

    @NotEmpty
    @Size(max = 30)
    private String name;

    private TeamImage teamImage;

    @Override
    public String getType() {
        return "anonymous";
    }

}
