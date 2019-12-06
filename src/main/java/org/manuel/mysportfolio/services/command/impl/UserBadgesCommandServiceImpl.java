package org.manuel.mysportfolio.services.command.impl;

import java.util.Set;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Badge;
import org.manuel.mysportfolio.model.entities.badges.UserBadges;
import org.manuel.mysportfolio.repositories.UserBadgesRepository;
import org.manuel.mysportfolio.services.command.UserBadgesCommandService;
import org.manuel.mysportfolio.services.query.UserBadgesQueryService;
import org.springframework.stereotype.Service;

@Service
@lombok.AllArgsConstructor
public class UserBadgesCommandServiceImpl implements UserBadgesCommandService {

	private final UserBadgesQueryService userBadgesQueryService;
	private final UserBadgesRepository userBadgesRepository;

	@Override
	public UserBadges save(final UserBadges userBadges) {
		return userBadgesRepository.save(userBadges);
	}

	@Override
	public UserBadges addBadges(final String userId, final Set<Badge> badges) {
		userBadgesQueryService.findByUser(userId);
		userBadgesRepository.addBadges(userId, badges);
		return userBadgesQueryService.findByUser(userId);
	}
}
