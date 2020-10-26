package org.manuel.mysportfolio;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Sport;
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
import org.manuel.mysportfolio.model.entities.match.type.FriendlyMatchType;
import org.manuel.mysportfolio.model.entities.team.PlainKitPart;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.model.entities.team.TeamKit;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam.UserInTeamRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class TestUtils {

  @SuppressWarnings("checkstyle:javadoctype")
  public static TeamDto createMockTeamDto() {
    return TeamDto.builder()
        .name(RandomStringUtils.randomAlphabetic(5))
        .teamImage(null)
        .teamKit(new TeamKit(new PlainKitPart(123), new PlainKitPart(321)))
        .build();
  }

  @SuppressWarnings("checkstyle:javadoctype")
  public static CompetitionDto createMockCompetitionDto() {
    return CompetitionDto.builder()
        .name(RandomStringUtils.randomAlphabetic(5))
        .sport(Sport.FOOTBALL)
        .defaultMatchDay(DayOfWeek.MONDAY)
        .description("Nice competition")
        .build();
  }

  @SuppressWarnings("checkstyle:javadoctype")
  public static Team createMockTeam() {
    final var team = new Team();
    team.setName(RandomStringUtils.randomAlphabetic(5));
    team.setTeamKit(new TeamKit(new PlainKitPart(123), new PlainKitPart(321)));
    return team;
  }

  @SuppressWarnings("checkstyle:javadoctype")
  public static UserInTeam createMockUserInTeam() {
    final var userInTeam = new UserInTeam();
    userInTeam.setFrom(LocalDate.now());
    userInTeam.setRoles(Collections.singleton(UserInTeamRole.PLAYER));
    return userInTeam;
  }

  @SuppressWarnings("checkstyle:javadoctype")
  public static TeamToUsers createMockTeamToUsers(final Team team) {
    return new TeamToUsers(null, null, team.getId(),
        Collections.singletonMap(team.getCreatedBy().get(),
            new UserInTeam(LocalDate.now(), null, UserInTeam.UserInTeamRole.PLAYER)),
        Collections.singleton(team.getCreatedBy().get()));
  }

  @SuppressWarnings("checkstyle:javadoctype")
  public static Competition createMockCompetition() {
    final var competition = new Competition();
    competition.setName(RandomStringUtils.randomAlphabetic(5));
    competition.setSport(Sport.FOOTBALL);
    competition.setDefaultMatchDay(DayOfWeek.SATURDAY);
    competition.setVersion(0L);
    return competition;
  }

  @SuppressWarnings("checkstyle:javadoctype")
  public static AnonymousTeam createMockAnonymousTeam() {
    final var anonymousTeam = new AnonymousTeam();
    anonymousTeam.setName(RandomStringUtils.randomAlphabetic(5));
    return anonymousTeam;
  }

  @SuppressWarnings("checkstyle:javadoctype")
  public static AnonymousTeamDto createMockAnonymousTeamDto() {
    return AnonymousTeamDto.builder()
        .name(RandomStringUtils.randomAlphabetic(5))
        .build();
  }

  @SuppressWarnings("checkstyle:javadoctype")
  public static RegisteredTeam createMockRegisteredTeam() {
    final var registeredTeam = new RegisteredTeam();
    registeredTeam.setTeamId(new ObjectId());
    return registeredTeam;
  }

  @SuppressWarnings("checkstyle:javadoctype")
  public static RegisteredTeamDto createMockRegisteredTeamDto(ObjectId teamId) {
    return RegisteredTeamDto.builder()
        .teamId(teamId.toString())
        .build();
  }

  @SuppressWarnings("checkstyle:javadoctype")
  public static <H extends TeamType, A extends TeamType> Match<H, A> createMockMatch(
      final H homeTeam, final A awayTeam, final String createdBy) {
    final var match = new Match<H, A>();
    match.setType(new FriendlyMatchType(Sport.FOOTBALL));
    match.setHomeTeam(homeTeam);
    match.setAwayTeam(awayTeam);
    match.setStartDate(Instant.now());
    match.setPlayedFor(Collections.singletonMap(createdBy, TeamOption.HOME_TEAM));
    match.setCreatedBy(createdBy);
    return match;
  }

  @SuppressWarnings("checkstyle:javadoctype")
  public static MatchEventDto createMockGoal(final TeamOption goalTeam) {
    final var matchEventDto = new MatchEventDto();
    matchEventDto.set("type", "goal");
    matchEventDto.set("team", goalTeam);
    return matchEventDto;
  }

  @SuppressWarnings("checkstyle:JavadocMethod")
  public static <H extends TeamTypeDto, A extends TeamTypeDto> MatchDto<H, A> createMockMatchDto(
      final H homeTeam, final A awayTeam, final int homeTeamGoalsNumber,
      final int awayTeamGoalsNumber, final Map<String, TeamOption> playedFor) {
    return createMockMatchDto(homeTeam, awayTeam, homeTeamGoalsNumber, awayTeamGoalsNumber,
        playedFor, new String[]{});
  }

  @SuppressWarnings("checkstyle:javadoctype")
  public static <H extends TeamTypeDto, A extends TeamTypeDto> MatchDto<H, A> createMockMatchDto(
      final H homeTeam, final A awayTeam, final int homeTeamGoalsNumber,
      final int awayTeamGoalsNumber, final Map<String, TeamOption> playedFor,
      final String... chips) {
    final var goals = IntStream.range(0, homeTeamGoalsNumber)
        .mapToObj(i -> createMockGoal(TeamOption.HOME_TEAM)).collect(Collectors.toList());
    goals.addAll(IntStream.range(0, awayTeamGoalsNumber)
        .mapToObj(i -> createMockGoal(TeamOption.AWAY_TEAM)).collect(Collectors.toList()));
    return MatchDto.builder()
        .type(new FriendlyMatchType(Sport.FOOTBALL))
        .playedFor(playedFor)
        .homeTeam(homeTeam)
        .awayTeam(awayTeam)
        .startDate(Instant.now().minus(1, ChronoUnit.DAYS))
        .events(goals)
        .chips(Arrays.asList(chips))
        .build();
  }

  @SuppressWarnings("checkstyle:javadoctype")
  public static Authentication createAuthentication(final String userId) {
    final Set<GrantedAuthority> authorities = Collections
        .singleton(new SimpleGrantedAuthority("ROLE_USER"));
    final OAuth2User principal = new DefaultOAuth2User(authorities,
        Collections.singletonMap("sub", userId), "sub");
    return new OAuth2AuthenticationToken(principal, authorities, "sub");
  }
}
