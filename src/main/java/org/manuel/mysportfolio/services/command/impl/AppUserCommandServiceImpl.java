package org.manuel.mysportfolio.services.command.impl;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.model.entities.usernotification.UserNotificationStatus;
import org.manuel.mysportfolio.repositories.AppUserRepository;
import org.manuel.mysportfolio.repositories.UserNotificationRepository;
import org.manuel.mysportfolio.services.UserNotificationHandler;
import org.manuel.mysportfolio.services.command.AppUserCommandService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@lombok.AllArgsConstructor
class AppUserCommandServiceImpl implements AppUserCommandService {

    private final AppUserRepository appUserRepository;
    private final UserNotificationHandler userNotificationHandler;
    private final UserNotificationRepository userNotificationRepository;

    @Override
    public AppUser save(@NotNull final AppUser appUser) {
        return appUserRepository.save(appUser);
    }


    @Override
    public void acceptUserNotification(final ObjectId notificationId) {
        userNotificationHandler.acceptNotification(userNotificationRepository.findById(notificationId).get());
        updateStatus(notificationId);
    }

    @Override
    public void rejectUserNotification(final ObjectId notificationId) {
        userNotificationHandler.rejectNotification(userNotificationRepository.findById(notificationId).get());
        updateStatus(notificationId);
    }

    private void updateStatus(final ObjectId notificationId) {
        userNotificationRepository.findByIdIsAndStatusIsNull(notificationId).ifPresent(it -> {
                it.setStatus(UserNotificationStatus.REVISITED);
                userNotificationRepository.save(it);
        });
    }
}
