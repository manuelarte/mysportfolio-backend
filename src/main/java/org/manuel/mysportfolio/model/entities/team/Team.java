package org.manuel.mysportfolio.model.entities.team;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.manuel.mysportfolio.model.TeamInfo;
import org.manuel.mysportfolio.model.entities.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "teams")
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = true)
@lombok.NoArgsConstructor
public class Team extends BaseEntity implements TeamInfo {

  @NotEmpty
  @Size(max = 30)
  private String name;

  private TeamImage teamImage;

  @NotNull
  private TeamKit<KitPart, KitPart> teamKit;

  public Team(final String name, final TeamImage teamImage, final TeamKit teamKit) {
    super(null, null, null, null, null, null);
    this.name = name;
    this.teamImage = teamImage;
    this.teamKit = teamKit;
  }

}
