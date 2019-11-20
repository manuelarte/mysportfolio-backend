package org.manuel.mysportfolio.services.command;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.user.AppUser;

public interface AppUserCommandService {

    AppUser save(AppUser appUser);

    void acceptUserNotification(ObjectId notificationId);

    void rejectUserNotification(ObjectId notificationId);

}
