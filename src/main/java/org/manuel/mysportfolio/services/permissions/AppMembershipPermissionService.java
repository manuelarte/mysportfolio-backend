package org.manuel.mysportfolio.services.permissions;

import io.github.manuelarte.mysportfolio.model.documents.BaseDocument;
import io.github.manuelarte.mysportfolio.model.documents.user.AppMembership;
import java.time.Instant;
import java.time.Year;
import java.time.ZoneOffset;
import java.util.function.Predicate;
import org.manuel.mysportfolio.services.query.BaseDocumentQueryService;
import org.manuel.mysportfolio.services.query.CompetitionQueryService;
import org.manuel.mysportfolio.services.query.MatchQueryService;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.manuel.mysportfolio.userpermissions.membership.AbstractMembership;
import org.manuel.mysportfolio.userpermissions.membership.AdvanceMembership;
import org.manuel.mysportfolio.userpermissions.membership.FreeMembership;
import org.manuel.mysportfolio.userpermissions.membership.NoobMembership;
import org.manuel.mysportfolio.userpermissions.membership.PremiumMembership;
import org.springframework.security.oauth2.core.user.OAuth2User;

@lombok.AllArgsConstructor
public enum AppMembershipPermissionService {
  FREE(AppMembership.FREE, new FreeMembership()),
  NOOB(AppMembership.NOOB, new NoobMembership()),
  ADVANCE(AppMembership.ADVANCE, new AdvanceMembership()),
  PREMIUM(AppMembership.PREMIUM, new PremiumMembership());

  private final AppMembership appMembership;
  private final AbstractMembership abstractMembership;

  public Predicate<OAuth2User> canSaveTeam(final TeamQueryService teamQueryService,
      final Year year) {
    final var reference = year.atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);
    return canSave(teamQueryService, reference);
  }

  public Predicate<OAuth2User> canSaveCompetition(
      final CompetitionQueryService competitionQueryService, final Year year) {
    final var reference = year.atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);
    return canSave(competitionQueryService, reference);
  }

  public Predicate<OAuth2User> canSaveMatch(final MatchQueryService matchQueryService, final Instant reference) {
    return canSave(matchQueryService, reference);
  }

  private <T extends BaseDocument> Predicate<OAuth2User> canSave(final BaseDocumentQueryService<T> baseDocumentQueryService, final Instant reference) {
    return oAuth2User -> {
      final var userRestrictions  = this.abstractMembership.getMatchRestrictions();
      final var externalUserId = oAuth2User.getAttributes().get("sub").toString();
      return userRestrictions.parallelStream().allMatch(userRestInt -> {
        final var maxNumberOfMatchesInPeriod = userRestInt.getUserRestriction().getMaxNumber();
        final var interval = userRestInt.getInterval(reference);
        return baseDocumentQueryService.countAllByCreatedByInInterval(externalUserId, interval) < maxNumberOfMatchesInPeriod;
      });
    };
  }

}
