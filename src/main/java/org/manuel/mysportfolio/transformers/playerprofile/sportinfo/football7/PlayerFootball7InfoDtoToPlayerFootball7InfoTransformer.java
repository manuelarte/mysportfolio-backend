package org.manuel.mysportfolio.transformers.playerprofile.sportinfo.football7;

import io.github.manuelarte.mysportfolio.model.documents.player.sportinfo.PlayerFootball7Info;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.sportinfo.PlayerFootball7InfoDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class PlayerFootball7InfoDtoToPlayerFootball7InfoTransformer implements
    Function<PlayerFootball7InfoDto, PlayerFootball7Info> {

  @Override
  public PlayerFootball7Info apply(
      final PlayerFootball7InfoDto playerProfileFootball7InfoDto) {
    final var output = new PlayerFootball7Info();
    output.setMainPosition(playerProfileFootball7InfoDto.getMainPosition());
    output.setAlternativePositions(playerProfileFootball7InfoDto.getAlternativePositions());
    output.setSkills(playerProfileFootball7InfoDto.getSkills());
    return output;
  }
}
