package org.manuel.mysportfolio.controllers;

import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.services.query.AppUserQueryService;

@lombok.experimental.UtilityClass
public class Util {

  public static AppUser getUser(final AppUserQueryService appUserQueryService,
      final UserIdProvider userIdProvider,
      final String externalUserId) {
    return ("me".equals(externalUserId)
        ? appUserQueryService.findByExternalId(userIdProvider.getUserId()) :
        appUserQueryService.findByExternalId(externalUserId))
        .orElseThrow(() -> new EntityNotFoundException(AppUser.class, externalUserId));
  }

}
