package org.manuel.mysportfolio.controllers.query.user;

import io.github.manuelarte.spring.queryparameter.QueryParameter;
import java.util.Optional;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.controllers.Util;
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
import org.manuel.mysportfolio.validations.UserExists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/{externalUserId}/matches")
@Validated
@lombok.AllArgsConstructor
public class UserMatchQueryController {

  private final AppUserQueryService appUserQueryService;
  private final MatchQueryService matchQueryService;
  private final PlayersPerformanceQueryService playersPerformanceQueryService;
  private final MatchToMatchDtoTransformer matchToMatchDtoTransformer;
  private final PerformanceToPerformanceDtoTransformer performanceToPerformanceDtoTransformer;
  private final UserIdProvider userIdProvider;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<UserMatchDto>> findAllMyUserMatches(
      @PathVariable @UserExists final String externalUserId,
      @PageableDefault(sort = "startDate", direction = Sort.Direction.DESC) final Pageable pageable,
      @QueryParameter(entity = Match.class) final Optional<Query> query) {
    final var appUser = Util.getUser(appUserQueryService, userIdProvider, externalUserId);
    final Page<Match<TeamType, TeamType>> page;
    if (query.isPresent()) {
      page = matchQueryService.findAllBy(query.get(), pageable, externalUserId);
    } else {
      page = matchQueryService.findAllCreatedBy(pageable, externalUserId);
    }
    return ResponseEntity.ok(page.map(
        m -> new UserMatchDto(matchToMatchDtoTransformer.apply(m), getPerformance(m, appUser))));
  }

  private PerformanceDto getPerformance(final Match<TeamType, TeamType> match,
      final AppUser appUser) {
    return playersPerformanceQueryService
        .findByMatchIdAndPlayerId(match.getId(), appUser.getExternalId())
        .map(performanceToPerformanceDtoTransformer)
        .orElse(null);
  }

}
