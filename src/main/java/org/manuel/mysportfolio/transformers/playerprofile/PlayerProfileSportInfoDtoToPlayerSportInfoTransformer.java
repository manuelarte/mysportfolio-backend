package org.manuel.mysportfolio.transformers.playerprofile;

import java.util.Optional;
import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileSportInfoDto;
import org.manuel.mysportfolio.model.entities.player.PlayerProfileSportInfo;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerProfileSportInfoDtoToPlayerSportInfoTransformer implements
    Function<PlayerProfileSportInfoDto, PlayerProfileSportInfo> {

  private final PlayerProfileFootballInfoDtoToPlayerProfileFootbalInfoTransformer playerProfileFootballInfoDtoToPlayerProfileFootbalInfoTransformer;
  private final PlayerProfileFutsalInfoDtoToPlayerProfileFutsalInfoTransformer playerProfileFutsalInfoDtoToPlayerProfileFutsalInfoTransformer;

  @Override
  public PlayerProfileSportInfo apply(final PlayerProfileSportInfoDto playerProfileSportInfo) {
    final var output = new PlayerProfileSportInfo();
    Optional.ofNullable(playerProfileSportInfo.getFootballInfo()).ifPresent(it ->
        output.setFootballInfo(playerProfileFootballInfoDtoToPlayerProfileFootbalInfoTransformer.apply(it))
    );
    Optional.ofNullable(playerProfileSportInfo.getFutsalInfo()).ifPresent(it ->
        output.setFutsalInfo(playerProfileFutsalInfoDtoToPlayerProfileFutsalInfoTransformer.apply(it))
    );
    return output;
  }

}
