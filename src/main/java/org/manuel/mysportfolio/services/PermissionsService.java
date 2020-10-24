package org.manuel.mysportfolio.services;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.function.Predicate;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.entities.user.AppMembership;
import org.manuel.mysportfolio.repositories.AppUserRepository;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.repositories.TeamToUsersRepository;
import org.manuel.mysportfolio.services.query.CompetitionQueryService;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@lombok.AllArgsConstructor
public class PermissionsService {

  private final TeamQueryService teamQueryService;
  private final CompetitionQueryService competitionQueryService;
  private final TeamToUsersRepository teamToUsersRepository;
  private final MatchRepository matchRepository;
  private final AppUserRepository appUserRepository;
  private final Clock clock;

  public boolean canSaveTeam() {
    final var oAuth2User = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal());
    return saveTeamRestrictions(Year.now(clock)).test(oAuth2User);
  }

  public boolean canSaveCompetition() {
    final var oAuth2User = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal());
    return saveCompetitionRestrictions(Year.now(clock)).test(oAuth2User);
  }

  public boolean canSaveMatch() {
    final var oAuth2User = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal());
    return saveMatchRestrictions().test(oAuth2User);
  }

  public boolean isTeamAdmin(final ObjectId teamId) {
    final var userId = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal()).getAttributes().get("sub");
    return teamToUsersRepository.findByTeamId(teamId).filter(it -> it.getAdmins().contains(userId))
        .isPresent();
  }

  /**
   * - The user can only update its own user in team only when the user is already there - Admin can
   * edit users in team.
   *
   * @return Whether it's allowed to save user in team
   */
  public boolean canSaveUserInTeam(final ObjectId teamId, final String userToModify) {
    final var userId = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal()).getAttributes().get("sub");
    final var byTeamId = teamToUsersRepository.findByTeamId(teamId)
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("Team to user entry not found for team %s", teamId.toString())));
    final var alreadyExistingPlayer = byTeamId.getUsers().containsKey(userId);
    final var modifyingItsOwnUser = userId.equals(userToModify);
    return alreadyExistingPlayer && modifyingItsOwnUser;
  }

  public boolean canSavePlayerProfile(final String externalId, final Year year) {
    final var minimumYear = Year.from(this.appUserRepository.findByExternalId(externalId).get().getCreatedDate().get()
        .atZone(ZoneId.systemDefault()));
    return year.compareTo(minimumYear) > -1;
  }

  private Predicate<OAuth2User> saveTeamRestrictions(final Year year) {
    return oAuth2User -> {
      final AppMembership appMembership = (AppMembership) oAuth2User.getAttributes()
          .get("app-membership");
      return appMembership.canSaveTeam(teamQueryService, year).test(oAuth2User);
    };
  }

  private Predicate<OAuth2User> saveCompetitionRestrictions(final Year year) {
    return oAuth2User -> {
      final AppMembership appMembership = (AppMembership) oAuth2User.getAttributes()
          .get("app-membership");
      return appMembership.canSaveCompetition(competitionQueryService, year).test(oAuth2User);
    };
  }

  private Predicate<OAuth2User> saveMatchRestrictions() {
    return oAuth2User -> {
      final AppMembership appMembership = (AppMembership) oAuth2User.getAttributes()
          .get("app-membership");
      return appMembership.canSaveMatch(matchRepository, LocalDate.now(clock)).test(oAuth2User);
    };
  }

}
