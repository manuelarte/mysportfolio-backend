package org.manuel.mysportfolio;

import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.RegisteredTeam;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.manuel.mysportfolio.model.entities.team.Team;

public class TestUtils {

    public static Team createMockTeam() {
        final var team = new Team();
        team.setName(RandomStringUtils.randomAlphabetic(5));
        return team;
    }

    public static AnonymousTeam createMockAnonymousTeam() {
        final var anonymousTeam = new AnonymousTeam();
        anonymousTeam.setName(RandomStringUtils.randomAlphabetic(5));
        return anonymousTeam;
    }

    public static RegisteredTeam createMockRegisteredTeam() {
        final var registeredTeam = new RegisteredTeam();
        registeredTeam.setTeamId(new ObjectId());
        return registeredTeam;
    }

    public static Match createMockMatch(final TeamType homeTeam, final TeamType awayTeam) {
        final var match = new Match();
        match.setSport(Sport.FOOTBALL);
        match.setType(SportType.ELEVEN_A_SIDE);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        match.setCreator(new ObjectId().toString());
        return match;
    }
}
