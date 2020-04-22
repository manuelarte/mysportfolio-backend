package org.manuel.mysportfolio.model.entities.teamtouser;

import io.github.manuelarte.spring.manuelartevalidation.constraints.FromAndToDate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.FromAndToDate.FromToType;
import io.github.manuelarte.spring.manuelartevalidation.constraints.fromto.FromDate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.fromto.ToDate;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.PastOrPresent;

@FromAndToDate(FromToType.FROM_LOWER_THAN_OR_EQUAL_TO_TO)
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class UserInTeam {

  @PastOrPresent
  @FromDate
  private LocalDate from;
  @ToDate
  private LocalDate to;
  private Set<UserInTeamRole> roles;

  public UserInTeam(final LocalDate from, final LocalDate to, final UserInTeamRole... roles) {
    this(from, to, Set.of(roles));
  }

  public enum UserInTeamRole {
    PLAYER, CAPTAIN, COACH, MANAGER, SUPPORTER
  }

}
