package org.manuel.mysportfolio.transformers;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.dtos.match.MatchInListDto;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchToMatchInListDtoTransformerTest {

    @Mock
    private TeamRepository teamRepository;

    private static final AnonymousTeamToTeamDtoTransformer ANONYMOUS_TEAM_TO_TEAM_DTO_TRANSFORMER =
            new AnonymousTeamToTeamDtoTransformer();

    private MatchToMatchInListDtoTransformer matchToMatchInListDtoTransformer;

    @BeforeEach
    public void setUp() {
        final var registeredTeamToTeamDtoTransformer = new RegisteredTeamToTeamDtoTransformer(teamRepository);
        final var teamTypeToTeamDtoTransformer = new TeamTypeToTeamDtoTransformer(registeredTeamToTeamDtoTransformer, ANONYMOUS_TEAM_TO_TEAM_DTO_TRANSFORMER);
        matchToMatchInListDtoTransformer =
                new MatchToMatchInListDtoTransformer(teamTypeToTeamDtoTransformer);
    }

    @Test
    public void test() {
        final var match = new Match<AnonymousTeam, AnonymousTeam>();
        match.setId(new ObjectId());
        match.setHomeTeam(TestUtils.createMockAnonymousTeam());
        match.setAwayTeam(TestUtils.createMockAnonymousTeam());

        final var expected = MatchInListDto.builder()
                .id(match.getId().toString())
                .homeTeam(match.getHomeTeam().getName())
                .homeGoals(2)
                .awayTeam(match.getAwayTeam().getName())
                .awayGoals(1).build();
        final var actual = matchToMatchInListDtoTransformer.apply(match);
        assertEquals(actual, expected);
    }

}
