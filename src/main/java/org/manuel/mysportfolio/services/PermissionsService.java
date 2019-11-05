package org.manuel.mysportfolio.services;

import org.manuel.mysportfolio.model.entities.user.AppMembership;
import org.manuel.mysportfolio.repositories.CompetitionRepository;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.function.Predicate;

@Service
@lombok.AllArgsConstructor
public class PermissionsService {

    private final TeamRepository teamRepository;
    private final CompetitionRepository competitionRepository;
    private final MatchRepository matchRepository;

    public boolean canSaveTeam() {
        final var oAuth2User = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return saveTeamRestrictions().test(oAuth2User);
    }

    public boolean canSaveCompetition() {
        final var oAuth2User = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return saveCompetitionRestrictions().test(oAuth2User);
    }

    public boolean canSaveMatch() {
        final var oAuth2User = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return saveMatchRestrictions().test(oAuth2User);
    }

    private Predicate<OAuth2User> saveTeamRestrictions() {
        final Predicate<OAuth2User> maxTeams = oAuth2User -> {
            final AppMembership appMembership = (AppMembership) oAuth2User.getAttributes().get("app-membership");
            return appMembership.canSaveTeam(teamRepository).test(oAuth2User);
        };
        return maxTeams;
    }

    private Predicate<OAuth2User> saveCompetitionRestrictions() {
        final Predicate<OAuth2User> maxCompetitions = oAuth2User -> {
            final AppMembership appMembership = (AppMembership) oAuth2User.getAttributes().get("app-membership");
            return appMembership.canSaveCompetition(competitionRepository).test(oAuth2User);
        };
        return maxCompetitions;
    }

    private Predicate<OAuth2User> saveMatchRestrictions() {
        final Predicate<OAuth2User> maxMatchesInOneWeek = oAuth2User -> {
            final AppMembership appMembership = (AppMembership) oAuth2User.getAttributes().get("app-membership");
            return appMembership.canSaveMatch(matchRepository, LocalDate.now()).test(oAuth2User);
        };
        return maxMatchesInOneWeek;
    }

}
