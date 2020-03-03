package org.manuel.mysportfolio.services.query.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.model.entities.usernotification.UserNotification;
import org.manuel.mysportfolio.repositories.AppUserRepository;
import org.manuel.mysportfolio.repositories.UserNotificationRepository;
import org.manuel.mysportfolio.services.query.AppUserQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@lombok.AllArgsConstructor
class AppUserQueryServiceImpl implements AppUserQueryService {

  private final AppUserRepository appUserRepository;
  private final UserNotificationRepository userNotificationRepository;

  @Override
  public Optional<AppUser> findOne(final ObjectId id) {
    return appUserRepository.findById(id);
  }

  @Override
  public Optional<AppUser> findByExternalId(final String externalId) {
    return appUserRepository.findByExternalId(externalId);
  }

  @Override
  public Page<AppUser> findAll(final Pageable pageable) {
    return appUserRepository.findAll(pageable);
  }

  @Override
  public Set<AppUser> findByExternalIds(final Collection<String> externalIds) {
    return appUserRepository.findByExternalIdInAndRegistrationTokenNotNull(externalIds);
  }

  @Override
  public Page<UserNotification> getUserNotifications(final Pageable pageable,
      final String externalId) {
    return userNotificationRepository.findAllByToIsAndStatusIsNull(pageable, externalId);
  }

}
