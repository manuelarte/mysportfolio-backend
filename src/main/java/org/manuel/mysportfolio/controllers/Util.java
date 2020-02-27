package org.manuel.mysportfolio.controllers;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.services.query.AppUserQueryService;

@lombok.experimental.UtilityClass
public class Util {

  public static AppUser getUser(final AppUserQueryService appUserQueryService,
      final UserIdProvider userIdProvider,
      final String userId) {
    return ("me".equals(userId) ?
        appUserQueryService.findByExternalId(userIdProvider.getUserId()) :
        appUserQueryService.findOne(new ObjectId(userId)))
        .orElseThrow(() -> new EntityNotFoundException(AppUser.class, userId));
  }

}
