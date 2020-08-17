package org.manuel.mysportfolio.model.entities.usernotification;

import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;

@lombok.Data
@lombok.EqualsAndHashCode(callSuper = true)
public class TeamAddUserNotification extends UserNotification {

  @NotNull
  private String from;

  @NotNull
  private ObjectId teamId;

  public TeamAddUserNotification() {
    super();
  }

  public TeamAddUserNotification(final ObjectId id, final Long version, final String from,
      final String to, final ObjectId teamId) {
    super(id, version, to, null);
    this.from = from;
    this.teamId = teamId;
  }

}
