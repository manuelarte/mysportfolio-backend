package org.manuel.mysportfolio.services.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.ITConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(ITConfiguration.class)
@ExtendWith({SpringExtension.class})
class MatchCommandServiceTest {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchQueryService matchQueryService;

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
        final var criteria = Criteria.where("startDate").gt(instant.toString());
        query.addCriteria(criteria);

        final var allByQuery = matchQueryService.findQueryAllCreatedBy(query, Pageable.unpaged(), "123456789");
        assertEquals(1, allByQuery.getTotalElements());
        assertMatch(expected, allByQuery.getContent().get(0));
        // TODO it's failing and don't know why, because in live it's working
    }

    private void assertMatch(final Match expected, final Match actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getStartDate().toEpochMilli(), actual.getStartDate().toEpochMilli());
    }


}