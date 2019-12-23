package org.manuel.mysportfolio.listeners;

import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.model.events.CompetitionCreatedEvent;
import org.manuel.mysportfolio.services.command.UserBadgesCommandService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class CompetitionCreatedEventListener implements ApplicationListener<CompetitionCreatedEvent> {

	private final UserBadgesCommandService userBadgesCommandService;
	private final UserIdProvider userIdProvider;

	@Override
	public void onApplicationEvent(final CompetitionCreatedEvent event) {
		final var userId = userIdProvider.getUserId();
		userBadgesCommandService.addBadges(userId, event);
	}

}
