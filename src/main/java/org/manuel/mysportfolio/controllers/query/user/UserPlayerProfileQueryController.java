package org.manuel.mysportfolio.controllers.query.user;

import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileDto;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.services.query.AppUserQueryService;
import org.manuel.mysportfolio.services.query.PlayerProfileQueryService;
import org.manuel.mysportfolio.transformers.PlayerProfileToPlayerProfileDtoTransformer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/{userId}/player")
@lombok.AllArgsConstructor
public class UserPlayerProfileQueryController {

  private final AppUserQueryService appUserQueryService;
  private final PlayerProfileQueryService playerProfileQueryService;
  private final PlayerProfileToPlayerProfileDtoTransformer playerProfileToPlayerProfileDtoTransformer;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PlayerProfileDto> getPlayer(@PathVariable final String userId) {
    appUserQueryService.findByExternalId(userId)
        .orElseThrow(() -> new EntityNotFoundException(AppUser.class, userId));
    return ResponseEntity.ok(playerProfileToPlayerProfileDtoTransformer.apply(playerProfileQueryService.getByExternalId(userId)));
  }

}
