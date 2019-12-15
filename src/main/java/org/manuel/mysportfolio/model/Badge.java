package org.manuel.mysportfolio.model;

public enum Badge {

	FOOTBALL_FIRST_MATCH(100, "https://drive.google.com/uc?id=1N29Dwi4dKVhy11TLUjcXB0nhOHXGixIl"),
	FOOTBALL_FIRST_MATCH_WON(50, "https://drive.google.com/uc?id=1AJATBwdfaGNKjnqObGItWRw-Tbay0pOe"),
	FOOTBALL_FIRST_GOAL(50, "https://drive.google.com/uc?id=1CwutQso_fdHgAz3swluW-1lNfJmSW32A"),
	FOOTBALL_FIRST_HATTRICK(200, "https://drive.google.com/uc?id=1WuRTDzrM-tPMyht9bSQ-_17G6h3p8Nus"),
	FOOTBALL_FIRST_ASSIST(20, null),

	FUTSAL_FIRST_MATCH(100, "https://drive.google.com/uc?id=18HuWT_FSCG0RSmmA5PD4t38mimUMUBWm"),
	FUTSAL_FIRST_MATCH_WON(50, "https://drive.google.com/uc?id=1GVuxzxikDoBoynT5Df2PFmDL9sQ2GLli"),
	FUTSAL_FIRST_GOAL(50, "https://drive.google.com/uc?id=1lnQiHawCi1tGye0wwB-Ie7XlG9caDuGb"),
	FUTSAL_FIRST_HATTRICK(200, "https://drive.google.com/uc?id=1U7qV0HZYAn_--kriwIznev6_cA8vx-tK"),
	FUTSAL_FIRST_ASSIST(20, null),

	TEAM_FIRST_ADDED(100, null),
	COMPETITION_FOOTBALL_FIRST_ADDED(50, null),
	COMPETITION_FUTSAL_FIRST_ADDED(50, null);

	@lombok.Getter
	private final int points;

	@lombok.Getter
	private final String imageUrl;

	Badge(final int points, final String imageUrl) {
		this.points = points;
		this.imageUrl = imageUrl;
	}
}
