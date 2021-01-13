package org.manuel.mysportfolio.controllers.command.user;

import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerSportInfoDto;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerSportsInfoDto;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.sportinfo.PlayerFootball7InfoDto;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.sportinfo.PlayerFootballInfoDto;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.sportinfo.PlayerFutsalInfoDto;
import java.time.Year;
import javax.validation.constraints.PastOrPresent;
import org.manuel.mysportfolio.services.command.PlayerProfileCommandService;
import org.manuel.mysportfolio.transformers.playerprofile.PlayerSportsInfoDtoToProfileSportsInfoTransformer;
import org.manuel.mysportfolio.transformers.playerprofile.PlayerSportsInfoToPlayerSportsInfoDtoTransformer;
import org.manuel.mysportfolio.transformers.playerprofile.sportinfo.PlayerSportInfoDtoToPlayerSportInfoTransformer;
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
  private final PlayerSportsInfoDtoToProfileSportsInfoTransformer playerSportsInfoDtoToProfileSportsInfoTransformer;
  private final PlayerSportsInfoToPlayerSportsInfoDtoTransformer playerSportsInfoToPlayerSportsInfoDtoTransformer;
  private final PlayerSportInfoDtoToPlayerSportInfoTransformer transformer;


  @PutMapping(value = "{year}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PlayerSportsInfoDto> updatePlayerSportInfo(
      @PathVariable @UserExists final String userId,
      @PastOrPresent @PathVariable final Year year,
      @RequestBody final PlayerSportsInfoDto playerSportsInfoDto) {
    final var updated = playerProfileCommandService.updateForYear(userId, year,
        playerSportsInfoDtoToProfileSportsInfoTransformer.apply(playerSportsInfoDto));
    return ResponseEntity.ok(playerSportsInfoToPlayerSportsInfoDtoTransformer
        .apply(userId, year, updated));
  }

  @PutMapping(value = "/{year}/FOOTBALL", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PlayerSportInfoDto<?>> updateYearPlayerFootballInfo(
      @PathVariable @UserExists final String userId,
      @PastOrPresent @PathVariable final Year year,
      @RequestBody final PlayerFootballInfoDto playerFootballInfoDto) {
    updatePlayerSportInfoForYearAndSport(userId, year, playerFootballInfoDto);
    return ResponseEntity.ok(playerFootballInfoDto);
  }

  @PutMapping(value = "/{year}/FOOTBALL_7", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PlayerSportInfoDto<?>> updateYearPlayerFootball7Info(
      @PathVariable @UserExists final String userId,
      @PastOrPresent @PathVariable final Year year,
      @RequestBody final PlayerFootball7InfoDto playerFootball7InfoDto) {
    updatePlayerSportInfoForYearAndSport(userId, year, playerFootball7InfoDto);
    return ResponseEntity.ok(playerFootball7InfoDto);
  }

  @PutMapping(value = "/{year}/FUTSAL", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PlayerSportInfoDto<?>> updateYearPlayerFutsalInfo(
      @PathVariable @UserExists final String userId,
      @PastOrPresent @PathVariable final Year year,
      @RequestBody final PlayerFutsalInfoDto playerFutsalInfoDto) {
    updatePlayerSportInfoForYearAndSport(userId, year, playerFutsalInfoDto);
    return ResponseEntity.ok(playerFutsalInfoDto);
  }

  private void updatePlayerSportInfoForYearAndSport(
      final String userId, final Year year, final PlayerSportInfoDto<?> playerSportInfo) {
    playerProfileCommandService.updateForYearAndSport(userId, year, transformer.apply(playerSportInfo));
  }

}
