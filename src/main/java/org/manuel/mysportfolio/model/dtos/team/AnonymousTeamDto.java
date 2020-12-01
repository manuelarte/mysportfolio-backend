package org.manuel.mysportfolio.model.dtos.team;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.manuelarte.mysportfolio.model.Constants;
import io.github.manuelarte.mysportfolio.model.documents.team.TeamImage;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Dto to be used if the home/away team is an anonymous team when user inputs a match.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeName("anonymous")
@lombok.AllArgsConstructor
@lombok.Value
@lombok.extern.jackson.Jacksonized @lombok.Builder(toBuilder = true)
public class AnonymousTeamDto implements TeamTypeDto {

  @NotEmpty
  @Size(max = Constants.TEAM_NAME_MAX_CHARACTERS)
  private final String name;

  private final TeamKitDto<KitPartDto, KitPartDto> teamKit;

  private final TeamImage teamImage;

}
