package org.manuel.mysportfolio.repositories;

import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.manuel.mysportfolio.model.entities.match.RegisteredTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class MatchRepositoryTest {

    @Autowired
    private MatchRepository matchRepository;

    @DisplayName("save match with two anonymous teams")
    @Test
    public void testSaveMatchWithAnonymousTeams() {
        final var match = new Match<AnonymousTeam, AnonymousTeam>();
        match.setHomeTeam(createMockAnonymousTeam());
        match.setAwayTeam(createMockAnonymousTeam());

        final Match saved = matchRepository.save(match);
    }

    @DisplayName("save match with one registered team and one anonymous teams")
    @Test
    public void testSaveMatchWithOneAnonymousTeam() {
        final var match = new Match<RegisteredTeam, AnonymousTeam>();
        match.setHomeTeam(createMockRegisteredTeam());
        match.setAwayTeam(createMockAnonymousTeam());

        final Match saved = matchRepository.save(match);
    }

    private AnonymousTeam createMockAnonymousTeam() {
        final var anonymousTeam = new AnonymousTeam();
        anonymousTeam.setName(RandomStringUtils.randomAlphabetic(5));
        return anonymousTeam;
    }

    private RegisteredTeam createMockRegisteredTeam() {
        final var registeredTeam = new RegisteredTeam();
        registeredTeam.setId(new ObjectId());
        return registeredTeam;
    }

}
