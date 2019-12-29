package org.manuel.mysportfolio.listeners;

import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.model.events.PlayersPerformanceUpdatedEvent;
import org.manuel.mysportfolio.model.events.TeamCreatedEvent;
import org.manuel.mysportfolio.services.command.UserBadgesCommandService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayersPerformanceUpdatedEventListener implements ApplicationListener<PlayersPerformanceUpdatedEvent> {

	private final UserBadgesCommandService userBadgesCommandService;
	private final UserIdProvider userIdProvider;

	@Override
	public void onApplicationEvent(final PlayersPerformanceUpdatedEvent event) {
		final var userId = userIdProvider.getUserId();
		userBadgesCommandService.addBadges(userId, event);
	}

}
