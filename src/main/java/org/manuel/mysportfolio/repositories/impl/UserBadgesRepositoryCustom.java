package org.manuel.mysportfolio.repositories.impl;

import java.util.Set;

import org.manuel.mysportfolio.model.Badge;

public interface UserBadgesRepositoryCustom {

	void addBadges(String userId, Set<Badge> badges);
}
