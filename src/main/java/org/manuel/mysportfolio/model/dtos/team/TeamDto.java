package org.manuel.mysportfolio.model.dtos.team;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.New;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.PartialUpdate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.Update;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import org.manuel.mysportfolio.model.TeamInfo;
import org.manuel.mysportfolio.model.entities.team.KitPart;
import org.manuel.mysportfolio.model.entities.team.TeamImage;
import org.manuel.mysportfolio.model.entities.team.TeamKit;

/**
 * Dto to use when the user is registering a new team or when the team info is displayed.
 */
@Immutable
@JsonDeserialize(builder = TeamDto.TeamDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class TeamDto implements TeamInfo {

  @Null(groups = {New.class, PartialUpdate.class})
  @NotNull(groups = Update.class)
  private final String id;

  @Null(groups = New.class)
  @NotNull(groups = {Update.class, PartialUpdate.class})
  private final Long version;

  @NotEmpty(groups = {New.class, Update.class})
  @Size(max = 30)
  private final String name;

  private final TeamImage teamImage;

  @NotNull(groups = {New.class, Update.class})
  private final TeamKit<KitPart, KitPart> teamKit;

  @Null(groups = {New.class, Update.class, PartialUpdate.class})
  private final String createdBy;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class TeamDtoBuilder {

  }
}
