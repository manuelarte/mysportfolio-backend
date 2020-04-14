package org.manuel.mysportfolio.services.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Set;
import javax.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.ItConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.type.FriendlyMatchType;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@Import(ItConfiguration.class)
@ExtendWith({SpringExtension.class})
class MatchCommandServiceTest {

  @Inject
  private MatchRepository matchRepository;

  @Inject
  private MatchQueryService matchQueryService;

  @DisplayName("load match by query for sport")
  @Test
  public void testGetMatchesWithStartDateGreaterThan() {
    final var expected = new Match<AnonymousTeam, AnonymousTeam>();
    expected.setHomeTeam(TestUtils.createMockAnonymousTeam());
    expected.setAwayTeam(TestUtils.createMockAnonymousTeam());
    expected.setType(new FriendlyMatchType(Sport.FOOTBALL));
    expected.setStartDate(Instant.now());
    expected.setCreatedBy("123456789");
    expected.setPlayedFor(Collections.singletonMap("123456789", TeamOption.HOME_TEAM));

    final var notExpected = new Match<AnonymousTeam, AnonymousTeam>();
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

    try {
      final Set<GrantedAuthority> authorities = Collections
          .singleton(new SimpleGrantedAuthority("ROLE_USER"));
      final OAuth2User principal = new DefaultOAuth2User(authorities,
          Collections.singletonMap("sub", "123456789"), "sub");
      final Authentication authentication = new OAuth2AuthenticationToken(principal, authorities,
          "sub");
      SecurityContextHolder.getContext().setAuthentication(authentication);
      final var allByQuery = matchQueryService
          .findAllBy(query, Pageable.unpaged(), "123456789");
      assertEquals(1, allByQuery.getTotalElements());
      assertMatch(expected, allByQuery.getContent().get(0));
    } finally {
      SecurityContextHolder.clearContext();
    }

  }

  private void assertMatch(final Match<?, ?> expected, final Match<?, ?> actual) {
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getStartDate().toEpochMilli(), actual.getStartDate().toEpochMilli());
  }


}