package org.manuel.mysportfolio.model.dtos.teamtousers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.New;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.Update;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;

@JsonDeserialize(builder = UserInTeamDto.UserInTeamDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class UserInTeamDto {

  @NotNull(groups = {New.class, Update.class})
  @PastOrPresent
  private final LocalDate from;

  private final LocalDate to;

  @NotEmpty(groups = {New.class, Update.class})
  @lombok.Singular
  private final Set<UserInTeam.UserInTeamRole> roles;

  @AssertTrue
  private boolean fromBeforeTo() {
    return from.compareTo(to) <= 0;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class UserInTeamDtoBuilder {

  }

}
