package org.manuel.mysportfolio.controllers.command.user;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfileSportInfo;
import java.time.Year;
import javax.validation.constraints.PastOrPresent;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileSportInfoDto;
import org.manuel.mysportfolio.services.command.PlayerProfileCommandService;
import org.manuel.mysportfolio.transformers.playerprofile.PlayerProfileSportInfoDtoToPlayerSportInfoTransformer;
import org.manuel.mysportfolio.validations.UserExists;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/{userId}/player")
@Validated
@lombok.AllArgsConstructor
public class UserPlayerProfileCommandController {

  private final PlayerProfileCommandService playerProfileCommandService;
  private final PlayerProfileSportInfoDtoToPlayerSportInfoTransformer transformer;

  @PutMapping(value = "{year}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PlayerProfileSportInfo> updatePlayerSportInfo(
      @PathVariable @UserExists final String userId,
      @PastOrPresent @PathVariable final Year year,
      @RequestBody final PlayerProfileSportInfoDto playerProfileSportInfo) {
    return ResponseEntity.ok(playerProfileCommandService.updateForYear(userId, year,
        transformer.apply(playerProfileSportInfo)));
  }

}
