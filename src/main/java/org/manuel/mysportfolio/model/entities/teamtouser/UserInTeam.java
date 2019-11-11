package org.manuel.mysportfolio.model.entities.teamtouser;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;
import java.util.Set;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class UserInTeam {

    public enum UserInTeamRole {
        PLAYER, COACH, MANAGER
    }

    private LocalDate from;

    private LocalDate to;

    private Set<UserInTeamRole> role;

    @AssertTrue
    private boolean fromBeforeTo() {
        return from.compareTo(to) <= 0;
    }

}
