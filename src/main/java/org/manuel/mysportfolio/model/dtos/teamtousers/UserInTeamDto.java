package org.manuel.mysportfolio.model.dtos.teamtousers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.spring.manuelartevalidation.constraints.FromAndToDate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.FromAndToDate.FromToType;
import io.github.manuelarte.spring.manuelartevalidation.constraints.fromto.FromDate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.fromto.ToDate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.New;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.Update;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;

@JsonDeserialize(builder = UserInTeamDto.UserInTeamDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@FromAndToDate(FromToType.FROM_LOWER_THAN_OR_EQUAL_TO_TO)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class UserInTeamDto {

  @FromDate
  @NotNull(groups = {New.class, Update.class})
  @PastOrPresent
  private final LocalDate from;

  @ToDate
  private final LocalDate to;

  @NotEmpty(groups = {New.class, Update.class})
  @lombok.Singular
  private final Set<UserInTeam.UserInTeamRole> roles;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class UserInTeamDtoBuilder {

  }

}
