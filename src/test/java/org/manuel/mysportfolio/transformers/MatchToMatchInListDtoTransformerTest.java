package org.manuel.mysportfolio.transformers;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.dtos.match.MatchInListDto;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.events.GoalMatchEvent;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.manuel.mysportfolio.transformers.match.AnonymousTeamToTeamInMatchInListDtoTransformer;
import org.manuel.mysportfolio.transformers.match.MatchToMatchInListDtoTransformer;
import org.manuel.mysportfolio.transformers.match.RegisteredTeamToTeamInMatchInListDtoTransformer;
import org.manuel.mysportfolio.transformers.match.TeamTypeToTeamInMatchListDtoTransformer;
import org.mockito.Mock;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchToMatchInListDtoTransformerTest {

    @Mock
    private TeamRepository teamRepository;

    private static final AnonymousTeamToTeamInMatchInListDtoTransformer ANONYMOUS_TEAM_TO_TEAM_IN_MATCH_IN_LIST_DTO_TRANSFORMER =
            new AnonymousTeamToTeamInMatchInListDtoTransformer();

    private MatchToMatchInListDtoTransformer matchToMatchInListDtoTransformer;

    @BeforeEach
    public void setUp() {
        final var registeredTeamToTeamInMatchInListDtoTransformer = new RegisteredTeamToTeamInMatchInListDtoTransformer(teamRepository);
        final var teamTypeToTeamInMatchInListDtoTransformer = new TeamTypeToTeamInMatchListDtoTransformer<>(registeredTeamToTeamInMatchInListDtoTransformer,
                ANONYMOUS_TEAM_TO_TEAM_IN_MATCH_IN_LIST_DTO_TRANSFORMER);
        matchToMatchInListDtoTransformer =
                new MatchToMatchInListDtoTransformer(teamTypeToTeamInMatchInListDtoTransformer);
    }

    @Test
    public void testMatchWithNoGoals() {
        final var match = new Match<AnonymousTeam, AnonymousTeam>();
        match.setId(new ObjectId());
        match.setHomeTeam(TestUtils.createMockAnonymousTeam());
        match.setAwayTeam(TestUtils.createMockAnonymousTeam());
        match.setPlayedFor(TeamOption.HOME_TEAM);

        final var expected = MatchInListDto.builder()
                .id(match.getId().toString())
                .homeTeam(match.getHomeTeam().getName())
                .homeGoals(0)
                .awayTeam(match.getAwayTeam().getName())
                .awayGoals(0)
                .playedFor(match.getPlayedFor())
                .build();
        final var actual = matchToMatchInListDtoTransformer.apply(match);
        assertEquals(actual, expected);
    }

    @Test
    public void testMatchWithOneHomeGoal() {
        final var match = new Match<AnonymousTeam, AnonymousTeam>();
        match.setId(new ObjectId());
        match.setHomeTeam(TestUtils.createMockAnonymousTeam());
        match.setAwayTeam(TestUtils.createMockAnonymousTeam());
        match.setPlayedFor(TeamOption.AWAY_TEAM);
        match.setEvents(Collections.singletonList(new GoalMatchEvent(null, TeamOption.HOME_TEAM, null, null, null, null, null)));

        final var expected = MatchInListDto.builder()
                .id(match.getId().toString())
                .homeTeam(match.getHomeTeam().getName())
                .homeGoals(1)
                .awayTeam(match.getAwayTeam().getName())
                .awayGoals(0)
                .playedFor(match.getPlayedFor())
                .build();
        final var actual = matchToMatchInListDtoTransformer.apply(match);
        assertEquals(actual, expected);
    }

}
