package org.manuel.mysportfolio.transformers.playerprofile.sportinfo.futsal;

import io.github.manuelarte.mysportfolio.model.documents.player.sportinfo.PlayerFutsalInfo;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.sportinfo.PlayerFutsalInfoDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class PlayerFutsalInfoDtoToPlayerFutsalInfoTransformer implements
    Function<PlayerFutsalInfoDto, PlayerFutsalInfo> {

  @Override
  public PlayerFutsalInfo apply(
      final PlayerFutsalInfoDto playerProfileFutsalInfoDto) {
    final var output = new PlayerFutsalInfo();
    output.setMainPosition(playerProfileFutsalInfoDto.getMainPosition());
    output.setAlternativePositions(playerProfileFutsalInfoDto.getAlternativePositions());
    output.setSkills(playerProfileFutsalInfoDto.getSkills());
    return output;
  }
}
