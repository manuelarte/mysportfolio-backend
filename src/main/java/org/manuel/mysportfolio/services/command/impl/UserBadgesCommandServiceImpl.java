package org.manuel.mysportfolio.services.command.impl;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.manuel.mysportfolio.Util;
import org.manuel.mysportfolio.badges.BadgeUtilHandler;
import org.manuel.mysportfolio.model.Badge;
import org.manuel.mysportfolio.model.entities.badges.UserBadges;
import org.manuel.mysportfolio.repositories.UserBadgesRepository;
import org.manuel.mysportfolio.services.command.UserBadgesCommandService;
import org.manuel.mysportfolio.services.query.UserBadgesQueryService;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

@Service
@lombok.AllArgsConstructor
public class UserBadgesCommandServiceImpl implements UserBadgesCommandService {

	private final UserBadgesQueryService userBadgesQueryService;
	private final UserBadgesRepository userBadgesRepository;
	private final BadgeUtilHandler badgeUtilHandler;

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

	@Override
	public <T extends ApplicationEvent> UserBadges addBadges(String userId, T event) {
		final var completedBadges = userBadgesQueryService.findByUser(userId).getBadges();

		final var newBadges = Arrays.stream(Badge.values()).filter(b -> !completedBadges.contains(b))
				.filter(b -> b.getPredicate().test(badgeUtilHandler, event))
				.collect(Collectors.toSet());
		return Util.doWithSystemAuthentication(() -> addBadges(userId, newBadges));
	}


}
