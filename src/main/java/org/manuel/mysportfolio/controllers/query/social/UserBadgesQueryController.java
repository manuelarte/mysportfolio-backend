package org.manuel.mysportfolio.controllers.query.social;

import java.util.Set;
import java.util.stream.Collectors;
import org.manuel.mysportfolio.model.dtos.AppBadgeDto;
import org.manuel.mysportfolio.services.query.UserBadgesQueryService;
import org.manuel.mysportfolio.transformers.BadgeToAppBadgeTransformer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/social/users/{userId}/badges")
@lombok.AllArgsConstructor
public class UserBadgesQueryController {

  private final UserBadgesQueryService userBadgesQueryService;
  private final BadgeToAppBadgeTransformer badgeToAppBadgeTransformer;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Set<AppBadgeDto>> findAllBadges(@PathVariable final String userId) {
    return ResponseEntity.ok(userBadgesQueryService.findByUser(userId).getBadges().stream()
        .map(badgeToAppBadgeTransformer).collect(Collectors.toSet()));
  }

}
