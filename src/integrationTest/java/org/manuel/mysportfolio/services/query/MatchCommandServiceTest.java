package org.manuel.mysportfolio.services.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.manuelarte.mysportfolio.model.Sport;
import io.github.manuelarte.mysportfolio.model.TeamOption;
import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.match.type.FriendlyMatchType;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import javax.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.ItConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootTest
@Import(ItConfiguration.class)
class MatchCommandServiceTest {

  @Inject
  private MatchRepository matchRepository;

  @Inject
  private MatchQueryService matchQueryService;

  @DisplayName("load match by query for sport")
  @Test
  public void testGetMatchesWithStartDateGreaterThan() {
    final var expected = new Match<>();
    expected.setHomeTeam(TestUtils.createMockAnonymousTeam());
    expected.setAwayTeam(TestUtils.createMockAnonymousTeam());
    expected.setType(new FriendlyMatchType(Sport.FOOTBALL));
    expected.setStartDate(Instant.now());
    expected.setCreatedBy("123456789");
    expected.setPlayedFor(Collections.singletonMap("123456789", TeamOption.HOME_TEAM));

    final var notExpected = new Match<>();
    notExpected.setType(new FriendlyMatchType(Sport.FUTSAL));
    notExpected.setHomeTeam(TestUtils.createMockAnonymousTeam());
    notExpected.setAwayTeam(TestUtils.createMockAnonymousTeam());
    notExpected.setStartDate(Instant.now().minus(80, ChronoUnit.DAYS));
    notExpected.setPlayedFor(Collections.singletonMap("123456789", TeamOption.HOME_TEAM));

    matchRepository.save(expected);
    matchRepository.save(notExpected);

    final Query query = new Query();
    final var criteria = Criteria.where("type.sport").is(Sport.FOOTBALL);
    query.addCriteria(criteria);

    final var allByQuery = TestUtils.doWithUserAuthentication(
        () -> matchQueryService.findAllBy(query, Pageable.unpaged(), "123456789")
    );
    assertEquals(1, allByQuery.getTotalElements());
    assertMatch(expected, allByQuery.getContent().get(0));
  }

  private void assertMatch(final Match<?, ?> expected, final Match<?, ?> actual) {
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getStartDate().toEpochMilli(), actual.getStartDate().toEpochMilli());
  }


}