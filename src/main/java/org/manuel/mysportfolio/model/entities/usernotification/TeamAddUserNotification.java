package org.manuel.mysportfolio.model.entities.usernotification;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.BaseEntity;

import javax.validation.constraints.NotNull;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Data
public class TeamAddUserNotification extends BaseEntity implements UserNotification {

    @NotNull
    private String from;

    @NotNull
    private String to;

    @NotNull
    private ObjectId teamId;

    private UserNotificationStatus status;

    public TeamAddUserNotification(final ObjectId id, final Long version, final String from, final String to, final ObjectId teamId) {
        super(id, version);
        this.from = from;
        this.to = to;
        this.teamId = teamId;
    }

    public void setStatus(final UserNotificationStatus status) {
        this.status = status;
    }

}
