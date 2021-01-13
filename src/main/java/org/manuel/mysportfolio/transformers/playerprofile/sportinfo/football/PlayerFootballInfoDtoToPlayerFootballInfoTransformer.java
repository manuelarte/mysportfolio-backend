package org.manuel.mysportfolio.transformers.playerprofile.sportinfo.football;

import io.github.manuelarte.mysportfolio.model.documents.player.sportinfo.PlayerFootballInfo;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.sportinfo.PlayerFootballInfoDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class PlayerFootballInfoDtoToPlayerFootballInfoTransformer implements
    Function<PlayerFootballInfoDto, PlayerFootballInfo> {

  @Override
  public PlayerFootballInfo apply(
      final PlayerFootballInfoDto playerProfileFootballInfoDto) {
    final var output = new PlayerFootballInfo();
    output.setMainPosition(playerProfileFootballInfoDto.getMainPosition());
    output.setAlternativePositions(playerProfileFootballInfoDto.getAlternativePositions());
    output.setSkills(playerProfileFootballInfoDto.getSkills());
    return output;
  }
}
