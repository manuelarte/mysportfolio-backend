package org.manuel.mysportfolio.listeners;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import org.manuel.mysportfolio.Util;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.model.Badge;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.model.entities.badges.UserBadges;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.manuel.mysportfolio.model.entities.match.events.GoalMatchEvent;
import org.manuel.mysportfolio.model.events.MatchCreatedEvent;
import org.manuel.mysportfolio.services.command.UserBadgesCommandService;
import org.manuel.mysportfolio.services.query.UserBadgesQueryService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class MatchCreatedEventListener implements ApplicationListener<MatchCreatedEvent> {

	private final UserBadgesQueryService userBadgesQueryService;
	private final UserBadgesCommandService userBadgesCommandService;
	private final UserIdProvider userIdProvider;

	@Override
	public void onApplicationEvent(final MatchCreatedEvent event) {
		final Match match = (Match) event.getSource();
		final var badges = new HashSet<Badge>();
		final var firstSportBadge = firstMatch(match.getSport());
		badges.add(firstSportBadge);
		final var matchWon = matchWon(userIdProvider.getUserId(), match);
		if (matchWon) {
			final var matchWonBadge = matchWonBadge(match.getSport());
			badges.add(matchWonBadge);
		}
		final var isGoal = isGoal(userIdProvider.getUserId(), match);
		if (isGoal) {
			final var goalBadge = goalBadge(match.getSport());
			badges.add(goalBadge);
		}
		final var isHattrick = isHattrick(userIdProvider.getUserId(), match);
		if (isHattrick) {
			final var hattrickBadge = hattrickBadge(match.getSport());
			badges.add(hattrickBadge);
		}
		final var userId = userIdProvider.getUserId();
		Util.doWithSystemAuthentication(() -> userBadgesCommandService.addBadges(userId, badges));
	}

	private Badge firstMatch(final Sport sport) {
		Badge toReturn = null;
		switch (sport) {
		case FOOTBALL:
			toReturn = Badge.FOOTBALL_FIRST_MATCH;
			break;
		case FUTSAL:
			toReturn = Badge.FUTSAL_FIRST_MATCH;
			break;
		}
		return toReturn;
	}

	private boolean matchWon(final String userId, final Match<? extends TeamType, ? extends TeamType> match) {
		TeamOption option = match.getPlayedFor().get(userId);
		if ((option == TeamOption.HOME_TEAM && match.getGoals(TeamOption.HOME_TEAM) > match.getGoals(TeamOption.AWAY_TEAM))
		|| (option == TeamOption.AWAY_TEAM && match.getGoals(TeamOption.HOME_TEAM) < match.getGoals(TeamOption.AWAY_TEAM))) {
			return true;
		} else {
			return false;
		}
	}

	private Badge matchWonBadge(final Sport sport) {
		Badge toReturn = null;
		switch (sport) {
		case FOOTBALL:
			toReturn = Badge.FOOTBALL_FIRST_MATCH_WON;
			break;
		case FUTSAL:
			toReturn = Badge.FUTSAL_FIRST_MATCH_WON;
			break;
		}
		return toReturn;
	}

	private boolean isGoal(final String userId, final Match<? extends TeamType, ? extends TeamType> match) {
		final TeamOption teamOption = match.getPlayedFor().get(userId);
		return Optional.ofNullable(match.getEvents()).orElse(Collections.emptyList()).stream()
			.filter(it -> it instanceof GoalMatchEvent)
			.filter(it -> ((GoalMatchEvent) it).getTeam().equals(teamOption))
			.filter(it -> userId.equals(((GoalMatchEvent) it).getPlayerId())).count() > 0;
	}

	private Badge goalBadge(final Sport sport) {
		Badge toReturn = null;
		switch (sport) {
		case FOOTBALL:
			toReturn = Badge.FOOTBALL_FIRST_GOAL;
			break;
		case FUTSAL:
			toReturn = Badge.FUTSAL_FIRST_GOAL;
			break;
		}
		return toReturn;
	}

	private boolean isHattrick(final String userId, final Match<? extends TeamType, ? extends TeamType> match) {
		final TeamOption teamOption = match.getPlayedFor().get(userId);
		return Optional.ofNullable(match.getEvents()).orElse(Collections.emptyList()).stream()
			.filter(it -> it instanceof GoalMatchEvent)
			.filter(it -> ((GoalMatchEvent) it).getTeam().equals(teamOption))
			.filter(it -> userId.equals(((GoalMatchEvent) it).getPlayerId())).count() >= 3;
	}

	private Badge hattrickBadge(final Sport sport) {
		Badge toReturn = null;
		switch (sport) {
		case FOOTBALL:
			toReturn = Badge.FOOTBALL_FIRST_HATTRICK;
			break;
		case FUTSAL:
			toReturn = Badge.FUTSAL_FIRST_HATTRICK;
			break;
		}
		return toReturn;
	}

}
