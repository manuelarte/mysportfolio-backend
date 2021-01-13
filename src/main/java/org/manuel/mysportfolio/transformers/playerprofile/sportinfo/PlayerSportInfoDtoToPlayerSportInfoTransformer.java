package org.manuel.mysportfolio.transformers.playerprofile.sportinfo;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerSportInfo;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerSportInfoDto;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.sportinfo.PlayerFootball7InfoDto;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.sportinfo.PlayerFootballInfoDto;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.sportinfo.PlayerFutsalInfoDto;
import java.util.function.Function;
import org.manuel.mysportfolio.transformers.playerprofile.sportinfo.football.PlayerFootballInfoDtoToPlayerFootballInfoTransformer;
import org.manuel.mysportfolio.transformers.playerprofile.sportinfo.football7.PlayerFootball7InfoDtoToPlayerFootball7InfoTransformer;
import org.manuel.mysportfolio.transformers.playerprofile.sportinfo.futsal.PlayerFutsalInfoDtoToPlayerFutsalInfoTransformer;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerSportInfoDtoToPlayerSportInfoTransformer implements
    Function<PlayerSportInfoDto<?>, PlayerSportInfo<?>> {

  private final PlayerFootballInfoDtoToPlayerFootballInfoTransformer
      playerFootballInfoDtoToPlayerFootballInfoTransformer;
  private final PlayerFootball7InfoDtoToPlayerFootball7InfoTransformer
      playerFootball7InfoDtoToPlayerFootball7InfoTransformer;
  private final PlayerFutsalInfoDtoToPlayerFutsalInfoTransformer
      playerFutsalInfoDtoToPlayerFutsalInfoTransformer;

  @Override
  public PlayerSportInfo<?> apply(final PlayerSportInfoDto playerSportInfo) {
    if (playerSportInfo instanceof PlayerFootballInfoDto) {
      final var footballInfoDto = (PlayerFootballInfoDto) playerSportInfo;
      return playerFootballInfoDtoToPlayerFootballInfoTransformer.apply(footballInfoDto);
    } else if (playerSportInfo instanceof PlayerFootball7InfoDto) {
      final var football7InfoDto = (PlayerFootball7InfoDto) playerSportInfo;
      return playerFootball7InfoDtoToPlayerFootball7InfoTransformer.apply(football7InfoDto);
    } else if (playerSportInfo instanceof PlayerFutsalInfoDto) {
      final var futsalInfoDto = (PlayerFutsalInfoDto) playerSportInfo;
      return playerFutsalInfoDtoToPlayerFutsalInfoTransformer.apply(futsalInfoDto);
    } else {
      return null;
    }
  }

}
