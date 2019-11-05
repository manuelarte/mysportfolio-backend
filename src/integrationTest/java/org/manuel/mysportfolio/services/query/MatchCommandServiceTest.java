package org.manuel.mysportfolio.services.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.ITConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import(ITConfiguration.class)
@ExtendWith({SpringExtension.class})
class MatchCommandServiceTest {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchQueryService matchQueryService;

    @DisplayName("load match by query for sport")
    @Test
    public void testGetMatchesWithStartDateGreaterThan() {
        final var expected = new Match<AnonymousTeam, AnonymousTeam>();
        expected.setHomeTeam(TestUtils.createMockAnonymousTeam());
        expected.setAwayTeam(TestUtils.createMockAnonymousTeam());
        expected.setSport(Sport.FOOTBALL);
        expected.setStartDate(Instant.now());
        expected.setCreatedBy("123456789");

        final var notExpected = new Match<AnonymousTeam, AnonymousTeam>();
        notExpected.setSport(Sport.FUTSAL);
        notExpected.setHomeTeam(TestUtils.createMockAnonymousTeam());
        notExpected.setAwayTeam(TestUtils.createMockAnonymousTeam());
        notExpected.setStartDate(Instant.now().minus(80, ChronoUnit.DAYS));

        matchRepository.save(expected);
        matchRepository.save(notExpected);

        final Query query = new Query();
        final var criteria = Criteria.where("sport").is(Sport.FOOTBALL);
        query.addCriteria(criteria);

        try {
            final Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
            final OAuth2User principal = new DefaultOAuth2User(authorities, Collections.singletonMap("sub", "123456789"), "sub");
            final Authentication authentication = new OAuth2AuthenticationToken(principal, authorities, "sub");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final var allByQuery = matchQueryService.findQueryAllCreatedBy(query, Pageable.unpaged(), "123456789");
            assertEquals(1, allByQuery.getTotalElements());
            assertMatch(expected, allByQuery.getContent().get(0));
        } finally {
            SecurityContextHolder.clearContext();
        }

    }

    private void assertMatch(final Match expected, final Match actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getStartDate().toEpochMilli(), actual.getStartDate().toEpochMilli());
    }


}