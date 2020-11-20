package org.manuel.mysportfolio.transformers.usernotification;

import io.github.manuelarte.mysportfolio.model.documents.team.Team;
import java.util.Optional;
import java.util.function.Function;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.dtos.usernotification.TeamAddUserNotificationDto;
import org.manuel.mysportfolio.model.entities.usernotification.TeamAddUserNotification;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.manuel.mysportfolio.transformers.team.TeamToTeamDtoTransformer;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class TeamAddUserNotificationToTeamAddUserNotificationDtoTransformer implements
    Function<TeamAddUserNotification, TeamAddUserNotificationDto> {

  private final TeamQueryService teamQueryService;
  private final TeamToTeamDtoTransformer teamToTeamDtoTransformer;

  @Override
  public TeamAddUserNotificationDto apply(final TeamAddUserNotification teamAddUserNotification) {
    final var team = teamQueryService.findOne(teamAddUserNotification.getTeamId())
        .orElseThrow(() -> new EntityNotFoundException(Team.class,
            teamAddUserNotification.getTeamId().toString()));
    return TeamAddUserNotificationDto.builder()
        .id(Optional.ofNullable(teamAddUserNotification.getId()).map(ObjectId::toString)
            .orElse(null))
        .version(teamAddUserNotification.getVersion())
        .from(teamAddUserNotification.getFrom())
        .to(teamAddUserNotification.getTo())
        .team(teamToTeamDtoTransformer.apply(team))
        .status(teamAddUserNotification.getStatus())
        .build();
  }

}
