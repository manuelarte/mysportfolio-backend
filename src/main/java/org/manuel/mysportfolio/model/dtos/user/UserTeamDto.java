package org.manuel.mysportfolio.model.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.New;
import javax.validation.constraints.NotNull;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.dtos.teamtousers.UserInTeamDto;

@JsonDeserialize(builder = UserTeamDto.UserTeamDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class UserTeamDto {

  @NotNull(groups = {New.class})
  private final TeamDto team;

  private final UserInTeamDto userInTeam;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class UserTeamDtoBuilder {

  }

}
