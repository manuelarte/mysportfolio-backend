package org.manuel.mysportfolio.model.dtos.team;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import org.manuel.mysportfolio.model.TeamInfo;
import org.manuel.mysportfolio.model.entities.team.KitPart;
import org.manuel.mysportfolio.model.entities.team.TeamImage;
import org.manuel.mysportfolio.model.entities.team.TeamKit;
import org.manuel.mysportfolio.validation.NewEntity;
import org.manuel.mysportfolio.validation.PartialUpdateEntity;
import org.manuel.mysportfolio.validation.UpdateEntity;

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

  @Null(groups = {NewEntity.class, PartialUpdateEntity.class})
  @NotNull(groups = UpdateEntity.class)
  private final String id;

  @Null(groups = NewEntity.class)
  @NotNull(groups = {UpdateEntity.class, PartialUpdateEntity.class})
  private final Long version;

  @NotEmpty(groups = {NewEntity.class, UpdateEntity.class})
  @Size(max = 30)
  private final String name;

  private final TeamImage teamImage;

  @NotNull(groups = {NewEntity.class, UpdateEntity.class})
  private final TeamKit<KitPart, KitPart> teamKit;

  @Null(groups = {NewEntity.class, UpdateEntity.class, PartialUpdateEntity.class})
  private final String createdBy;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class TeamDtoBuilder {

  }
}
