package org.manuel.mysportfolio.services.query;

import org.manuel.mysportfolio.model.entities.badges.UserBadges;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserBadgesQueryService {

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM') or #externalId == authentication.principal.attributes['sub']")
	UserBadges findByUser(final String externalId);
}
