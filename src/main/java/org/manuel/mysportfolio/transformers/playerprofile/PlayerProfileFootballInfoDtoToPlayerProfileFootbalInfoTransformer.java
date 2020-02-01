package org.manuel.mysportfolio.transformers.playerprofile;

import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileFootballInfoDto;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileFutsalInfoDto;
import org.manuel.mysportfolio.model.entities.player.PlayerProfileFootballInfo;
import org.manuel.mysportfolio.model.entities.player.PlayerProfileFutsalInfo;
import org.springframework.stereotype.Component;

@Component
public class PlayerProfileFootballInfoDtoToPlayerProfileFootbalInfoTransformer implements
    Function<PlayerProfileFootballInfoDto, PlayerProfileFootballInfo> {

  @Override
  public PlayerProfileFootballInfo apply(final PlayerProfileFootballInfoDto playerProfileFootballInfoDto) {
    final var output = new PlayerProfileFootballInfo();
    output.setPreferredPosition(playerProfileFootballInfoDto.getPreferredPosition());
    output.setAlternativePositions(playerProfileFootballInfoDto.getAlternativePositions());
    output.setSkills(playerProfileFootballInfoDto.getSkills());
    return output;
  }
}
