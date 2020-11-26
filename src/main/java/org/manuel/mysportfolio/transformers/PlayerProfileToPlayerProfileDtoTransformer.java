package org.manuel.mysportfolio.transformers;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfile;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.dtos.playerprofile.PlayerProfileDto;
import org.manuel.mysportfolio.transformers.playerprofile.PlayerProfileSportInfoToPlayerProfileSportInfoDtoTransformer;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerProfileToPlayerProfileDtoTransformer implements
    Function<PlayerProfile, PlayerProfileDto> {

  private final PlayerProfileSportInfoToPlayerProfileSportInfoDtoTransformer transformer;

  @Override
  public PlayerProfileDto apply(final PlayerProfile playerProfile) {
    final var info = playerProfile.getInfo();
    final var mappedInfo = info.entrySet().stream()
        .collect(Collectors.toMap(Entry::getKey, it ->
            transformer.apply(playerProfile.getExternalId(), it.getKey(), it.getValue())));

    return PlayerProfileDto.builder()
        .id(Optional.ofNullable(playerProfile.getId()).map(ObjectId::toString).orElse(null))
        .info(mappedInfo)
        .build();
  }
}
