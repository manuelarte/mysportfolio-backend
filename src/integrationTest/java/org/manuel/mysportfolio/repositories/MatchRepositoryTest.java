package org.manuel.mysportfolio.repositories;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.RegisteredTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class MatchRepositoryTest {

    @Autowired
    private MatchRepository matchRepository;

    @DisplayName("save match with two anonymous teams")
    @Test
    public void testSaveMatchWithAnonymousTeams() {
        final var match = new Match<AnonymousTeam, AnonymousTeam>();
        match.setHomeTeam(TestUtils.createMockAnonymousTeam());
        match.setAwayTeam(TestUtils.createMockAnonymousTeam());

        matchRepository.save(match);
    }

    @DisplayName("save match with one registered team and one anonymous teams")
    @Test
    public void testSaveMatchWithOneAnonymousTeam() {
        final var match = new Match<RegisteredTeam, AnonymousTeam>();
        match.setHomeTeam(TestUtils.createMockRegisteredTeam());
        match.setAwayTeam(TestUtils.createMockAnonymousTeam());

        matchRepository.save(match);
    }

    @DisplayName("update match with different lock version")
    @Test
    public void testUpdateMatchWithDifferentVersion() {
        final var match = new Match<RegisteredTeam, AnonymousTeam>();
        match.setHomeTeam(TestUtils.createMockRegisteredTeam());
        match.setAwayTeam(TestUtils.createMockAnonymousTeam());

        final var saved = matchRepository.save(match);
        saved.setVersion(5L);
        assertThrows(OptimisticLockingFailureException.class, () -> {
            matchRepository.save(saved);
        });
    }

    @DisplayName("load match by query for start date")
    @Test
    public void testGetMatchesWithStartDateGreaterThan() {
        final var expected = new Match<AnonymousTeam, AnonymousTeam>();
        expected.setHomeTeam(TestUtils.createMockAnonymousTeam());
        expected.setAwayTeam(TestUtils.createMockAnonymousTeam());
        expected.setStartDate(Instant.now());

        final var notExpected = new Match<AnonymousTeam, AnonymousTeam>();
        notExpected.setHomeTeam(TestUtils.createMockAnonymousTeam());
        notExpected.setAwayTeam(TestUtils.createMockAnonymousTeam());
        notExpected.setStartDate(Instant.now().minus(80, ChronoUnit.DAYS));

        matchRepository.save(expected);
        matchRepository.save(notExpected);

        final Query query = new Query();
        final Instant instant = Instant.now().minus(16, ChronoUnit.DAYS);
        // it does not work with instants
        final var criteria = Criteria.where("startDate").gt(new Date(instant.toEpochMilli()));
        query.addCriteria(criteria);

        final var allByQuery = matchRepository.findQueryAllCreatedBy(query, Pageable.unpaged());
        assertEquals(1, allByQuery.getTotalElements());
        assertMatch(expected, allByQuery.getContent().get(0));
    }

    private void assertMatch(final Match expected, final Match actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getStartDate().toEpochMilli(), actual.getStartDate().toEpochMilli());
    }


}
