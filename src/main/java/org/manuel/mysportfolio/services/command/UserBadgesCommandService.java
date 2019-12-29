package org.manuel.mysportfolio.services.command;

import java.util.Set;

import org.manuel.mysportfolio.model.Badge;
import org.manuel.mysportfolio.model.entities.badges.UserBadges;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserBadgesCommandService {

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM')")
	UserBadges save(UserBadges userBadges);

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM')")
	UserBadges addBadges(String userId, Set<Badge> badges);

	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SYSTEM')")
	<T extends ApplicationEvent> UserBadges addBadges(String userId, T event);
}
