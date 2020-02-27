package org.manuel.mysportfolio.controllers.command.user;

import java.time.Year;
import javax.validation.constraints.PastOrPresent;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileSportInfoDto;
import org.manuel.mysportfolio.model.entities.player.PlayerProfileSportInfo;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.services.command.PlayerProfileCommandService;
import org.manuel.mysportfolio.services.query.AppUserQueryService;
import org.manuel.mysportfolio.transformers.playerprofile.PlayerProfileSportInfoDtoToPlayerSportInfoTransformer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/{userId}/player")
@lombok.AllArgsConstructor
public class UserPlayerProfileCommandController {

  private final AppUserQueryService appUserQueryService;
  private final PlayerProfileCommandService playerProfileCommandService;
  private final PlayerProfileSportInfoDtoToPlayerSportInfoTransformer transformer;

  @PutMapping(value = "{year}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PlayerProfileSportInfo> updatePlayerSportInfo(@PathVariable final String userId,
      @PastOrPresent @PathVariable final Year year, @RequestBody final PlayerProfileSportInfoDto playerProfileSportInfo) {
    appUserQueryService.findByExternalId(userId)
        .orElseThrow(() -> new EntityNotFoundException(AppUser.class, userId));
    return ResponseEntity.ok(playerProfileCommandService.updateForYear(userId, year,
        transformer.apply(playerProfileSportInfo)));
  }

}
