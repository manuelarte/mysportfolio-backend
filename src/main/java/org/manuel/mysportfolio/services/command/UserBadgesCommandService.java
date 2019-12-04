package org.manuel.mysportfolio.services.command;

import java.util.Set;

import org.manuel.mysportfolio.model.Badge;
import org.manuel.mysportfolio.model.entities.badges.UserBadges;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserBadgesCommandService {

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM')")
	UserBadges save(UserBadges userBadges);

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM')")
	UserBadges addBadges(String userId, Set<Badge> badges);
}
