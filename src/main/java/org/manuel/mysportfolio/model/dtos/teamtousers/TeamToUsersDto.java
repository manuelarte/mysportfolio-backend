package org.manuel.mysportfolio.model.dtos.teamtousers;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.New;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.PartialUpdate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.Update;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import org.bson.types.ObjectId;

@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.extern.jackson.Jacksonized @lombok.Builder(toBuilder = true)
public class TeamToUsersDto {

  @Null(groups = {New.class, PartialUpdate.class})
  @NotNull(groups = Update.class)
  private final ObjectId id;

  @Null(groups = New.class)
  @NotNull(groups = {Update.class, PartialUpdate.class})
  private final Long version;

  @NotEmpty(groups = {New.class, Update.class})
  @lombok.Singular
  private Map<String, UserInTeamDto> users;

  @NotEmpty(groups = {New.class, Update.class})
  @lombok.Singular
  private Set<String> admins;

}
