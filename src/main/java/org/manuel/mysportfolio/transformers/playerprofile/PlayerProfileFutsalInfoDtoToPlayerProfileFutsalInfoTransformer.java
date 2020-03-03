package org.manuel.mysportfolio.transformers.playerprofile;

import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileFutsalInfoDto;
import org.manuel.mysportfolio.model.entities.player.PlayerProfileFutsalInfo;
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
