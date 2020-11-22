package org.manuel.mysportfolio.services.command;

import io.github.manuelarte.mysportfolio.model.documents.user.AppUser;
import org.bson.types.ObjectId;

public interface AppUserCommandService {

  AppUser save(AppUser appUser);

  void acceptUserNotification(ObjectId notificationId);

  void rejectUserNotification(ObjectId notificationId);

}
