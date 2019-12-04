package org.manuel.mysportfolio.controllers.query.social;

import java.util.Set;

import org.manuel.mysportfolio.model.Badge;
import org.manuel.mysportfolio.services.query.UserBadgesQueryService;
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

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<Badge>> findAllBadges(@PathVariable final String userId) {
		return ResponseEntity.ok(userBadgesQueryService.findByUser(userId).getBadges());
	}

}
