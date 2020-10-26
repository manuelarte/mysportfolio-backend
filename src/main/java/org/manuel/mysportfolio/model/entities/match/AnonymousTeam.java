package org.manuel.mysportfolio.model.entities.match;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import org.manuel.mysportfolio.constants.Constants;
import org.manuel.mysportfolio.model.TeamInfo;
import org.manuel.mysportfolio.model.entities.team.KitPart;
import org.manuel.mysportfolio.model.entities.team.TeamImage;
import org.manuel.mysportfolio.model.entities.team.TeamKit;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class AnonymousTeam implements TeamType, TeamInfo {

  @NotEmpty
  @Size(max = Constants.TEAM_NAME_MAX_CHARACTERS)
  private String name;

  private TeamKit<KitPart, KitPart> teamKit;

  private TeamImage teamImage;

  @Override
  public String getType() {
    return "anonymous";
  }

}
