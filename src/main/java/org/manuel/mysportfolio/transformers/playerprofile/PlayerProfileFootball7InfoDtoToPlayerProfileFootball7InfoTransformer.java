package org.manuel.mysportfolio.transformers.playerprofile;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfileFootball7Info;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileFootball7InfoDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class PlayerProfileFootball7InfoDtoToPlayerProfileFootball7InfoTransformer implements
    Function<PlayerProfileFootball7InfoDto, PlayerProfileFootball7Info> {

  @Override
  public PlayerProfileFootball7Info apply(
      final PlayerProfileFootball7InfoDto playerProfileFootball7InfoDto) {
    final var output = new PlayerProfileFootball7Info();
    output.setPreferredPosition(playerProfileFootball7InfoDto.getPreferredPosition());
    output.setAlternativePositions(playerProfileFootball7InfoDto.getAlternativePositions());
    output.setSkills(playerProfileFootball7InfoDto.getSkills());
    return output;
  }
}
