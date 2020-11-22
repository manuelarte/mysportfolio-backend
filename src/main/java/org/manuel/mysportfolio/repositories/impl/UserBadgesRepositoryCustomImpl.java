package org.manuel.mysportfolio.repositories.impl;

import java.util.Set;
import org.manuel.mysportfolio.model.Badge;
import org.manuel.mysportfolio.model.entities.badges.UserBadges;
import org.manuel.mysportfolio.repositories.UserBadgesRepositoryCustom;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
class UserBadgesRepositoryCustomImpl implements UserBadgesRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  @Override
  public void addBadges(final String userId, final Set<Badge> badges) {
    final var query = Query.query(Criteria.where("userId").is(userId));
    final var update = new Update();
    badges.forEach(it -> update.addToSet("badges", it));
    mongoTemplate.upsert(query, update, UserBadges.class);
  }
}
