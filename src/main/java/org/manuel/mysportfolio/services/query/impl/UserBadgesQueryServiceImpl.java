package org.manuel.mysportfolio.services.query.impl;

import java.util.Collections;
import org.manuel.mysportfolio.model.entities.badges.UserBadges;
import org.manuel.mysportfolio.repositories.UserBadgesRepository;
import org.manuel.mysportfolio.services.query.UserBadgesQueryService;
import org.springframework.stereotype.Service;

@Service
@lombok.AllArgsConstructor
class UserBadgesQueryServiceImpl implements UserBadgesQueryService {

  private final UserBadgesRepository userBadgesRepository;

  @Override
  public UserBadges findByUser(final String externalId) {
    return userBadgesRepository.findByUserIdIs(externalId).orElseGet(() ->
        userBadgesRepository.save(new UserBadges(null, null, externalId, Collections.emptySet())));
  }

}
