package org.manuel.mysportfolio.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.badges.UserBadges;
import org.manuel.mysportfolio.repositories.impl.UserBadgesRepositoryCustom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBadgesRepository extends CrudRepository<UserBadges, ObjectId>, UserBadgesRepositoryCustom {

	Optional<UserBadges> findByUserIdIs(String userId);
}
