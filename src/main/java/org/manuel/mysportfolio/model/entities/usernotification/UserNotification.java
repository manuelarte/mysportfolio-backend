package org.manuel.mysportfolio.model.entities.usernotification;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Nullable;

@Document(collection = "user-notifications")
public interface UserNotification {

    ObjectId getId();

    Long getVersion();

    String getTo();

    @Nullable UserNotificationStatus getStatus();

    void setStatus(UserNotificationStatus status);

}
