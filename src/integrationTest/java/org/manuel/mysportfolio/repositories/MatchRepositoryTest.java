package org.manuel.mysportfolio.repositories;

import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.ITConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.RegisteredTeam;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.LocalDate;
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
        assertThrows(OptimisticLockingFailureException.class, () -> matchRepository.save(saved));
    }

    @DisplayName("load matches by created date, one match recorded yesterday")
    @Test
    public void testGetMatchesWithStartDateGreaterThan() {
        final var expected = createMatch("1234", Instant.now());
        final var notExpected = createMatch("1234", Instant.now().minus(80, ChronoUnit.DAYS));

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

    @DisplayName("load matches by created date, two matches expected recorded in same day")
    @Test
    public void testGetMatchesBetweenDate() {

        // match played yesterday and recorded yesterday
        final var expected = createMatch("123456", Instant.now().minus(1, ChronoUnit.DAYS));

        // match played long ago but recorded yesterday
        final var expected2 = createMatch("123456", Instant.now().minus(30, ChronoUnit.DAYS),
                Instant.now().minus(1, ChronoUnit.DAYS));

        // match played long long time ago and recorded long long time ago
        final var notExpected = createMatch("123456", Instant.now().minus(80, ChronoUnit.DAYS));

        matchRepository.save(expected);
        matchRepository.save(expected2);
        matchRepository.save(notExpected);

        final LocalDate localDate = LocalDate.now().minus(16, ChronoUnit.DAYS);

        final var count = matchRepository.countAllByCreatedDateBetweenAndCreatedBy(localDate, LocalDate.now(), "123456");
        assertEquals(2, count);
    }

    @DisplayName("load matches by teamId")
    @Test
    // TODO
    public void testGetMatchesForTeamId() {
        final var lookedTeam = TestUtils.createMockRegisteredTeam();
        final var homeTeamMatch = new Match<>();
        homeTeamMatch.setHomeTeam(lookedTeam);
        homeTeamMatch.setAwayTeam(TestUtils.createMockAnonymousTeam());
        homeTeamMatch.setStartDate(Instant.now());
        homeTeamMatch.setCreatedBy("1234");
        //homeTeamMatch.setCreatedDate(createdDate);

        final var awayTeamMatch = new Match<>();
        awayTeamMatch.setHomeTeam(TestUtils.createMockAnonymousTeam());
        awayTeamMatch.setAwayTeam(lookedTeam);
        awayTeamMatch.setStartDate(Instant.now());
        awayTeamMatch.setCreatedBy("1234");
        //awayTeamMatch.setCreatedDate(createdDate);

        matchRepository.save(homeTeamMatch);
        matchRepository.save(awayTeamMatch);

        final var homeTeamCriteria = Criteria.where("homeTeam.teamId").is(lookedTeam.getTeamId());
        final var awayTeamCriteria = Criteria.where("awayTeam.teamId").is(lookedTeam.getTeamId());

        // TODO not working now
        final var matchHomeTeamCriteria = matchRepository.findQueryAllCreatedBy(new Query(homeTeamCriteria), Pageable.unpaged());
        assertEquals(1, matchHomeTeamCriteria.getContent().size());
        assertMatch(homeTeamMatch, matchHomeTeamCriteria.getContent().get(0));

        final var matchAwayTeamCriteria = matchRepository.findQueryAllCreatedBy(new Query(awayTeamCriteria), Pageable.unpaged());
        assertEquals(1, matchAwayTeamCriteria.getContent().size());
        assertMatch(awayTeamMatch, matchAwayTeamCriteria.getContent().get(0));

        var criteria = new Criteria().orOperator(homeTeamCriteria, awayTeamCriteria);
        final var allByQuery = matchRepository.findQueryAllCreatedBy(new Query(criteria), Pageable.unpaged());
        assertEquals(2, allByQuery.getTotalElements());
        assertMatch(homeTeamMatch, allByQuery.getContent().get(0));
        assertMatch(awayTeamMatch, allByQuery.getContent().get(1));
    }

    @Test
    @DisplayName("test get season statistics")
    public void testGetMatchesForOneYear() {
        final var expected = matchRepository.save(createMatch(ITConfiguration.IT_USER_ID, Instant.now()));

        final var notExpectedPreviousYear = matchRepository.save(createMatch(ITConfiguration.IT_USER_ID,
            ZonedDateTime.now().minusYears(1).toInstant()));

        final var notExpectedAnotherSport = createMatch(ITConfiguration.IT_USER_ID, Instant.now());
        notExpectedAnotherSport.setSport(Sport.FUTSAL);
        matchRepository.save(notExpectedAnotherSport);

        final var notExpectedIsAnotherUser = createMatch(ITConfiguration.IT_USER_ID, Instant.now());
        notExpectedIsAnotherUser.setPlayedFor(Collections.singletonMap("otherUser", TeamOption.HOME_TEAM));
        matchRepository.save(notExpectedIsAnotherUser);

        final var from = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
        final var to = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
        final var actual = matchRepository.findAllByPlayedContainsAndSportIsAndStartDateIsBetween(ITConfiguration.IT_USER_ID, Sport.FOOTBALL, from, to);
        assertEquals(1, actual.size());
        assertEquals(expected.getId(), actual.get(0).getId());
    }

    private void assertMatch(final Match expected, final Match actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getStartDate().toEpochMilli(), actual.getStartDate().toEpochMilli());
    }

    private Match<TeamType, TeamType> createMatch(final String createdBy, final Instant startDate) {
        return createMatch(createdBy, startDate, startDate);
    }

    private Match<TeamType, TeamType> createMatch(final String createdBy, final Instant startDate, final Instant createdDate) {
        final Map<String, TeamOption> playedFor = new HashMap<>();
        playedFor.put(createdBy, TeamOption.HOME_TEAM);
        final var expected = new Match<>();
        expected.setHomeTeam(TestUtils.createMockAnonymousTeam());
        expected.setAwayTeam(TestUtils.createMockAnonymousTeam());
        expected.setPlayedFor(playedFor);
        expected.setStartDate(startDate);
        expected.setCreatedBy(createdBy);
        expected.setCreatedDate(createdDate);
        expected.setSport(Sport.FOOTBALL);
        return expected;
    }


}
