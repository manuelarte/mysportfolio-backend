package org.manuel.mysportfolio.model.dtos.usernotification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.mysportfolio.model.dtos.team.TeamDto;
import javax.validation.constraints.NotNull;
import org.manuel.mysportfolio.model.entities.usernotification.UserNotificationStatus;

@JsonDeserialize(builder = TeamAddUserNotificationDto.TeamAddUserNotificationDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class TeamAddUserNotificationDto implements UserNotificationDto {

  @NotNull
  private final String id;

  @NotNull
  private final Long version;

  @NotNull
  private final String from;

  @NotNull
  private final String to;

  @NotNull
  private final TeamDto team;

  private final UserNotificationStatus status;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class TeamAddUserNotificationDtoBuilder {

  }

}
