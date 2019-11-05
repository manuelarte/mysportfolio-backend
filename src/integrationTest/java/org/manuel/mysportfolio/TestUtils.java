package org.manuel.mysportfolio;

import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.manuel.mysportfolio.model.dtos.CompetitionDto;
import org.manuel.mysportfolio.model.dtos.match.MatchDto;
import org.manuel.mysportfolio.model.dtos.match.MatchEventDto;
import org.manuel.mysportfolio.model.dtos.team.AnonymousTeamDto;
import org.manuel.mysportfolio.model.dtos.team.RegisteredTeamDto;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.dtos.team.TeamTypeDto;
import org.manuel.mysportfolio.model.entities.Competition;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.RegisteredTeam;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.manuel.mysportfolio.model.entities.team.Team;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestUtils {

    public static TeamDto createMockTeamDto() {
        return TeamDto.builder()
                .name(RandomStringUtils.randomAlphabetic(5))
                .imageLink(null)
                .build();
    }

    public static CompetitionDto createMockCompetitionDto() {
        return CompetitionDto.builder()
                .name(RandomStringUtils.randomAlphabetic(5))
                .sport(Sport.FOOTBALL)
                .defaultMatchDay(DayOfWeek.MONDAY)
                .description("Nice competition")
                .build();
    }

    public static Team createMockTeam() {
        final var team = new Team();
        team.setName(RandomStringUtils.randomAlphabetic(5));
        return team;
    }

    public static Competition createMockCompetition() {
        final var competition = new Competition();
        competition.setName(RandomStringUtils.randomAlphabetic(5));
        competition.setSport(Sport.FOOTBALL);
        competition.setDefaultMatchDay(DayOfWeek.SATURDAY);
        competition.setVersion(0L);
        return competition;
    }

    public static AnonymousTeam createMockAnonymousTeam() {
        final var anonymousTeam = new AnonymousTeam();
        anonymousTeam.setName(RandomStringUtils.randomAlphabetic(5));
        return anonymousTeam;
    }

    public static AnonymousTeamDto createMockAnonymousTeamDto() {
        return AnonymousTeamDto.builder()
                .name(RandomStringUtils.randomAlphabetic(5))
                .build();
    }

    public static RegisteredTeam createMockRegisteredTeam() {
        final var registeredTeam = new RegisteredTeam();
        registeredTeam.setTeamId(new ObjectId());
        return registeredTeam;
    }

    public static RegisteredTeamDto createMockRegisteredTeamDto(ObjectId teamId) {
        return RegisteredTeamDto.builder()
                .teamId(teamId.toString())
                .build();
    }

    public static Match createMockMatch(final TeamType homeTeam, final TeamType awayTeam, final String createdBy) {
        final var match = new Match();
        match.setSport(Sport.FOOTBALL);
        match.setType(SportType.ELEVEN_A_SIDE);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        match.setCreatedBy(createdBy);
        return match;
    }

    public static MatchEventDto createMockGoal(final TeamOption goalTeam) {
        final var matchEventDto = new MatchEventDto();
        matchEventDto.set("type", "goal");
        matchEventDto.set("team", goalTeam);
        return matchEventDto;
    }

    public static <HT extends TeamTypeDto, AT extends TeamTypeDto> MatchDto<HT, AT> createMockMatchDto(final HT homeTeam, final AT awayTeam, final int homeTeamGoalsNumber, final int awayTeamGoalsNumber, final Map<String, TeamOption> playedFor) {
        final var goals = IntStream.range(0, homeTeamGoalsNumber).mapToObj(i -> createMockGoal(TeamOption.HOME_TEAM)).collect(Collectors.toList());
        goals.addAll(IntStream.range(0, awayTeamGoalsNumber).mapToObj(i -> createMockGoal(TeamOption.AWAY_TEAM)).collect(Collectors.toList()));
        return MatchDto.builder()
                .sport(Sport.FOOTBALL)
                .type(SportType.ELEVEN_A_SIDE)
                .playedFor(playedFor)
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .startDate(Instant.now().minus(1, ChronoUnit.DAYS))
                .events(goals)
                .build();
    }
}
