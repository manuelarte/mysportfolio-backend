package org.manuel.mysportfolio.model.entities.match;

import org.manuel.mysportfolio.model.TeamInfo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class AnonymousTeam implements TeamType, TeamInfo {

    @NotEmpty
    @Size(max = 30)
    private String name;

    @org.hibernate.validator.constraints.URL
    private String imageLink;

    @Override
    public String getType() {
        return "anonymous";
    }

}
