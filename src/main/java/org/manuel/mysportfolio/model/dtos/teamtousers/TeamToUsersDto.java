package org.manuel.mysportfolio.model.dtos.teamtousers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import org.manuel.mysportfolio.validation.NewEntity;
import org.manuel.mysportfolio.validation.PartialUpdateEntity;
import org.manuel.mysportfolio.validation.UpdateEntity;

@JsonDeserialize(builder = TeamToUsersDto.TeamToUsersDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class TeamToUsersDto {

  @Null(groups = {NewEntity.class, PartialUpdateEntity.class})
  @NotNull(groups = UpdateEntity.class)
  private final String id;

  @Null(groups = NewEntity.class)
  @NotNull(groups = {UpdateEntity.class, PartialUpdateEntity.class})
  private final Long version;

  @NotEmpty(groups = {NewEntity.class, UpdateEntity.class})
  @lombok.Singular
  private Map<String, UserInTeamDto> users;

  @NotEmpty(groups = {NewEntity.class, UpdateEntity.class})
  @lombok.Singular
  private Set<String> admins;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class TeamToUsersDtoBuilder {

  }

}
