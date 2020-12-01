package org.manuel.mysportfolio.model.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.New;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.Update;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.dtos.teamtousers.UserInTeamDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.extern.jackson.Jacksonized @lombok.Builder(toBuilder = true)
public class UserTeamDto {

  @NotNull(groups = {New.class, Update.class})
  @Valid
  private final TeamDto team;

  @NotNull(groups = {New.class, Update.class})
  @Valid
  private final UserInTeamDto userInTeam;

}
