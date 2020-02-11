package org.manuel.mysportfolio.model.entities.teamtouser;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.PastOrPresent;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class UserInTeam {

    public enum UserInTeamRole {
        PLAYER, CAPTAIN, COACH, MANAGER, SUPPORTER
    }

    @PastOrPresent
    private LocalDate from;

    private LocalDate to;

    private Set<UserInTeamRole> roles;

    public UserInTeam(final LocalDate from, final LocalDate to, final UserInTeamRole... roles) {
        this(from, to, Set.of(roles));
    }

    @AssertTrue
    private boolean fromBeforeTo() {
        return from.compareTo(to) <= 0;
    }

}
