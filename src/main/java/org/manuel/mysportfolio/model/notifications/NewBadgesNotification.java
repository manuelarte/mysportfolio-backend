package org.manuel.mysportfolio.model.notifications;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.Set;
import org.manuel.mysportfolio.model.dtos.AppBadgeDto;

@JsonTypeName("new-badges")
@lombok.AllArgsConstructor
@lombok.Builder
@lombok.Data
public class NewBadgesNotification implements UserNotification {

  private final Set<AppBadgeDto> badges;

  private final Reason reason;

  @lombok.AllArgsConstructor
  @lombok.Builder
  @lombok.Data
  public static class Reason {
    public enum Entity {
      MATCH, COMPETITION, TEAM, PLAYER_PERFORMANCE
    }
    private final Entity entity;
    private final String id;
  }

}
