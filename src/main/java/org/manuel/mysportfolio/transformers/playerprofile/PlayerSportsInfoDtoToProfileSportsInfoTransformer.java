package org.manuel.mysportfolio.transformers.playerprofile;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerSportsInfo;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerSportsInfoDto;
import java.util.Optional;
import java.util.function.Function;
import org.manuel.mysportfolio.transformers.playerprofile.sportinfo.football.PlayerFootballInfoDtoToPlayerFootballInfoTransformer;
import org.manuel.mysportfolio.transformers.playerprofile.sportinfo.football7.PlayerFootball7InfoDtoToPlayerFootball7InfoTransformer;
import org.manuel.mysportfolio.transformers.playerprofile.sportinfo.futsal.PlayerFutsalInfoDtoToPlayerFutsalInfoTransformer;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerSportsInfoDtoToProfileSportsInfoTransformer implements Function<PlayerSportsInfoDto, PlayerSportsInfo> {

  private final PlayerFootballInfoDtoToPlayerFootballInfoTransformer
      playerFootballInfoDtoToPlayerFootballInfoTransformer;
  private final PlayerFootball7InfoDtoToPlayerFootball7InfoTransformer
      playerFootball7InfoDtoToPlayerFootball7InfoTransformer;
  private final PlayerFutsalInfoDtoToPlayerFutsalInfoTransformer
      playerFutsalInfoDtoToPlayerFutsalInfoTransformer;

  @Override
  public PlayerSportsInfo apply(final PlayerSportsInfoDto profileSportsInfoDto) {
    return PlayerSportsInfo.builder()
        .football(
            Optional.ofNullable(
                profileSportsInfoDto.getFootball()).map(playerFootballInfoDtoToPlayerFootballInfoTransformer)
                .orElse(null))
        .football7(
            Optional.ofNullable(
                profileSportsInfoDto.getFootball7()).map(playerFootball7InfoDtoToPlayerFootball7InfoTransformer)
                .orElse(null))
        .futsal(
            Optional.ofNullable(
                profileSportsInfoDto.getFutsal()).map(playerFutsalInfoDtoToPlayerFutsalInfoTransformer)
                .orElse(null))
        .build();
  }
}
