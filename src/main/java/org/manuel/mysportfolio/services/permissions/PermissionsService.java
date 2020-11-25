package org.manuel.mysportfolio.services.permissions;

import io.github.manuelarte.mysportfolio.model.documents.BaseDocument;
import io.github.manuelarte.mysportfolio.model.documents.user.AppMembership;
import java.time.Clock;
import java.time.Instant;
import java.time.Year;
import java.time.ZoneId;
import java.util.Set;
import java.util.function.Function;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.config.properties.UserRestrictionsConfig;
import org.manuel.mysportfolio.config.properties.UserRestrictionsConfig.MembershipUserRestriction;
import org.manuel.mysportfolio.config.properties.UserRestrictionsConfig.UserRestriction;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.repositories.AppUserRepository;
import org.manuel.mysportfolio.repositories.TeamToUsersRepository;
import org.manuel.mysportfolio.services.query.BaseDocumentQueryService;
import org.manuel.mysportfolio.services.query.CompetitionQueryService;
import org.manuel.mysportfolio.services.query.MatchQueryService;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@lombok.AllArgsConstructor
public class PermissionsService {

  private final UserRestrictionsConfig userRestrictionsConfig;
  private final MatchQueryService matchQueryService;
  private final TeamQueryService teamQueryService;
  private final CompetitionQueryService competitionQueryService;
  private final TeamToUsersRepository teamToUsersRepository;
  private final AppUserRepository appUserRepository;
  private final Clock clock;

  public boolean canSaveTeam() {
    final var oAuth2User = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal());
    return this.canSave(oAuth2User, teamQueryService);
  }

  public boolean canSaveCompetition() {
    final var oAuth2User = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal());
    return this.canSave(oAuth2User, competitionQueryService);
  }

  public boolean canSaveMatch() {
    final var oAuth2User = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal());
    return this.canSave(oAuth2User, matchQueryService);
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

  private <T extends BaseDocument> boolean canSave(final OAuth2User auth2User, final BaseDocumentQueryService<T> baseDocumentQueryService) {
    final AppMembership appMembership = AppMembership.valueOf(auth2User.getAttributes().get("app-membership").toString());
    final var externalUserId = auth2User.getAttributes().get("sub").toString();
    final var membershipRestrictions = userRestrictionsConfig.of(appMembership);
    final var documentRestrictions = getUserRestrictionsOf(baseDocumentQueryService).apply(membershipRestrictions);
    return documentRestrictions.parallelStream().allMatch(userRestInt -> {
      final var maxNumberOfMatchesInPeriod = userRestInt.getMaxNumber();
      final var interval = userRestInt.getInterval(Instant.now(clock));
      return baseDocumentQueryService.countAllByCreatedByInInterval(externalUserId, interval) < maxNumberOfMatchesInPeriod;
    });
  }

  private <T extends BaseDocumentQueryService<?>> Function<MembershipUserRestriction, Set<UserRestriction>> getUserRestrictionsOf(
      final T clazz) {
    return m -> {
      if (clazz instanceof TeamQueryService) {
        return m.getTeams();
      } else if (clazz instanceof CompetitionQueryService) {
        return m.getCompetitions();
      } else if (clazz instanceof MatchQueryService) {
        return m.getMatches();
      } else {
        throw new RuntimeException();
      }
    };
  }

}
