package org.manuel.mysportfolio.transformers.playerprofile;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfileSportInfo;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileFootball7InfoDto;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileFootballInfoDto;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileFutsalInfoDto;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileSportInfoDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerProfileSportInfoDtoToPlayerSportInfoTransformer implements
    Function<PlayerProfileSportInfoDto<?>, PlayerProfileSportInfo<?>> {

  private final PlayerProfileFootballInfoDtoToPlayerProfileFootbalInfoTransformer
      playerProfileFootballInfoDtoToPlayerProfileFootbalInfoTransformer;
  private final PlayerProfileFootball7InfoDtoToPlayerProfileFootball7InfoTransformer
      playerProfileFootball7InfoDtoToPlayerProfileFootball7InfoTransformer;
  private final PlayerProfileFutsalInfoDtoToPlayerProfileFutsalInfoTransformer
      playerProfileFutsalInfoDtoToPlayerProfileFutsalInfoTransformer;

  @Override
  public PlayerProfileSportInfo<?> apply(final PlayerProfileSportInfoDto playerProfileSportInfo) {
    if (playerProfileSportInfo instanceof PlayerProfileFootballInfoDto) {
      final var footballInfoDto = (PlayerProfileFootballInfoDto) playerProfileSportInfo;
      return playerProfileFootballInfoDtoToPlayerProfileFootbalInfoTransformer.apply(footballInfoDto);
    } else if (playerProfileSportInfo instanceof PlayerProfileFootball7InfoDto) {
      final var football7InfoDto = (PlayerProfileFootball7InfoDto) playerProfileSportInfo;
      return playerProfileFootball7InfoDtoToPlayerProfileFootball7InfoTransformer.apply(football7InfoDto);
    } else if (playerProfileSportInfo instanceof PlayerProfileFutsalInfoDto) {
      final var futsalInfoDto = (PlayerProfileFutsalInfoDto) playerProfileSportInfo;
      return playerProfileFutsalInfoDtoToPlayerProfileFutsalInfoTransformer.apply(futsalInfoDto);
    } else {
      return null;
    }
  }

}
