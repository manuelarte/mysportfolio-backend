package org.manuel.mysportfolio.model;

public enum Badge {

	FOOTBALL_FIRST_MATCH(100),
	FOOTBALL_FIRST_MATCH_WON(50),
	FOOTBALL_FIRST_GOAL(50),
	FOOTBALL_FIRST_HATTRICK(200),
	FOOTBALL_FIRST_ASSIST(20),

	FUTSAL_FIRST_MATCH(100),
	FUTSAL_FIRST_MATCH_WON(50),
	FUTSAL_FIRST_GOAL(50),
	FUTSAL_FIRST_HATTRICK(200),
	FUTSAL_FIRST_ASSIST(20),

	TEAM_FIRST_ADDED(100),
	COMPETITION_FOOTBALL_FIRST_ADDED(50),
	COMPETITION_FUTSAL_FIRST_ADDED(50);

	@lombok.Getter
	private final int points;

	Badge(final int points) {
		this.points = points;
	}
}
