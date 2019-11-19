package org.manuel.mysportfolio.controllers.query.user;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.model.dtos.match.PerformanceDto;
import org.manuel.mysportfolio.model.dtos.user.UserMatchDto;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.services.query.AppUserQueryService;
import org.manuel.mysportfolio.services.query.MatchQueryService;
import org.manuel.mysportfolio.services.query.PlayersPerformanceQueryService;
import org.manuel.mysportfolio.transformers.PerformanceToPerformanceDtoTransformer;
import org.manuel.mysportfolio.transformers.match.MatchToMatchDtoTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/me/matches")
@AllArgsConstructor
public class UserMatchQueryController {

    private final AppUserQueryService appUserQueryService;
    private final MatchQueryService matchQueryService;
    private final PlayersPerformanceQueryService playersPerformanceQueryService;
    private final MatchToMatchDtoTransformer matchToMatchDtoTransformer;
    private final PerformanceToPerformanceDtoTransformer performanceToPerformanceDtoTransformer;
    private final UserIdProvider userIdProvider;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UserMatchDto>> findAllMyUserMatches(
            @PathVariable final String userId,
            @PageableDefault final Pageable pageable) {
        final AppUser appUser = getUser(userId);
        final Page<Match<TeamType, TeamType>> matches = matchQueryService.findAllCreatedBy(pageable, appUser.getExternalId());
        return ResponseEntity.ok(matches.map(m -> new UserMatchDto(matchToMatchDtoTransformer.apply(m), getPerformance(m, appUser))));
    }

    private final PerformanceDto getPerformance(final Match match, final AppUser appUser) {
        return playersPerformanceQueryService.findByMatchIdAndPlayerId(match.getId(), appUser.getExternalId()).map(performanceToPerformanceDtoTransformer)
                .orElse(null);
    }


    private AppUser getUser(final String userId) {
        final AppUser user;
        if ("me".equals(userId)) {
            user = appUserQueryService.findByExternalId(userIdProvider.getUserId()).get();
        } else {
            user = appUserQueryService.findOne(new ObjectId(userId)).get();
        }
        return user;
    }

}
