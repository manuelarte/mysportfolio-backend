package org.manuel.mysportfolio.controllers.query.user;

import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileDto;
import org.manuel.mysportfolio.services.query.PlayerProfileQueryService;
import org.manuel.mysportfolio.transformers.PlayerProfileToPlayerProfileDtoTransformer;
import org.manuel.mysportfolio.validations.UserExists;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/{userId}/player")
@Validated
@lombok.AllArgsConstructor
public class UserPlayerProfileQueryController {

  private final PlayerProfileQueryService playerProfileQueryService;
  private final PlayerProfileToPlayerProfileDtoTransformer playerProfileToPlayerProfileDtoTransformer;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PlayerProfileDto> getPlayer(@PathVariable @UserExists final String userId) {
    return ResponseEntity.ok(playerProfileToPlayerProfileDtoTransformer
        .apply(playerProfileQueryService.getByExternalId(userId)));
  }

}
