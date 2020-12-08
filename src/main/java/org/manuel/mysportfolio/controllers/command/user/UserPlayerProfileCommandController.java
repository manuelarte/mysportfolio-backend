package org.manuel.mysportfolio.controllers.command.user;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfileSportInfo;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileSportInfoDto;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.PastOrPresent;
import org.manuel.mysportfolio.services.command.PlayerProfileCommandService;
import org.manuel.mysportfolio.transformers.playerprofile.PlayerProfileSportInfoDtoToPlayerSportInfoTransformer;
import org.manuel.mysportfolio.transformers.playerprofile.PlayerProfileSportInfoToPlayerProfileSportInfoDtoTransformer;
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
  private final PlayerProfileSportInfoToPlayerProfileSportInfoDtoTransformer playerProfileSportInfoToPlayerProfileSportInfoDtoTransformer;

  @PutMapping(value = "{year}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<PlayerProfileSportInfoDto<?>>> updatePlayerSportInfo(
      @PathVariable @UserExists final String userId,
      @PastOrPresent @PathVariable final Year year,
      @RequestBody final List<PlayerProfileSportInfoDto<?>> playerProfileSportsInfo) {
    final var updated = playerProfileCommandService.updateForYear(userId, year,
        playerProfileSportsInfo.stream()
            .map(transformer).collect(Collectors.<PlayerProfileSportInfo<?>>toList()));
    return ResponseEntity.ok(playerProfileSportInfoToPlayerProfileSportInfoDtoTransformer
        .apply(userId, year, updated));
  }

}
