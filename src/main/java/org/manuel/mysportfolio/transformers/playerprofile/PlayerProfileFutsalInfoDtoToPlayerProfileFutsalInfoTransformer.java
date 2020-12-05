package org.manuel.mysportfolio.transformers.playerprofile;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfileFutsalInfo;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileFutsalInfoDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class PlayerProfileFutsalInfoDtoToPlayerProfileFutsalInfoTransformer implements
    Function<PlayerProfileFutsalInfoDto, PlayerProfileFutsalInfo> {

  @Override
  public PlayerProfileFutsalInfo apply(
      final PlayerProfileFutsalInfoDto playerProfileFutsalInfoDto) {
    final var output = new PlayerProfileFutsalInfo();
    output.setPreferredPosition(playerProfileFutsalInfoDto.getPreferredPosition());
    output.setAlternativePositions(playerProfileFutsalInfoDto.getAlternativePositions());
    output.setSkills(playerProfileFutsalInfoDto.getSkills());
    return output;
  }
}
