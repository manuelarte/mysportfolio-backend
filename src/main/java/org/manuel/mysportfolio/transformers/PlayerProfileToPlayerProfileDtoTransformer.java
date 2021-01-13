package org.manuel.mysportfolio.transformers;

import io.github.manuelarte.mysportfolio.model.documents.player.PlayerProfile;
import io.github.manuelarte.mysportfolio.model.documents.player.PlayerSportsInfo;
import io.github.manuelarte.mysportfolio.model.documents.user.AppUser;
import io.github.manuelarte.mysportfolio.model.dtos.playerprofile.PlayerProfileDto;
import java.time.Clock;
import java.time.Year;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.manuel.mysportfolio.services.query.AppUserQueryService;
import org.manuel.mysportfolio.transformers.playerprofile.PlayerSportsInfoToPlayerSportsInfoDtoTransformer;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class PlayerProfileToPlayerProfileDtoTransformer implements
    Function<PlayerProfile, PlayerProfileDto> {

  private final Clock clock;
  private final AppUserQueryService appUserQueryService;
  private final PlayerSportsInfoToPlayerSportsInfoDtoTransformer transformer;

  @Override
  public PlayerProfileDto apply(final PlayerProfile playerProfile) {
    final var creationDate = appUserQueryService.findByExternalId(playerProfile.getExternalId())
        .flatMap(AppUser::getCreatedDate)
        .orElseThrow(() -> new IllegalArgumentException("User needs to exist"));
    final var creationDateYear = Year.from(creationDate.atZone(ZoneId.systemDefault()));
    final var nowYear = Year.now(clock);
    long between = ChronoUnit.YEARS.between(creationDateYear, nowYear);
    final Stream<Year> yearStream = LongStream.range(0, between + 1).mapToObj(creationDateYear::plusYears);

    final var info = playerProfile.getInfo();
    final var mappedInfo = yearStream
        .collect(Collectors.toMap(year -> year, year ->
            transformer.apply(
                playerProfile.getExternalId(),
                year,
                Optional.ofNullable(info.get(year)).orElse(new PlayerSportsInfo()))));

    return PlayerProfileDto.builder()
        .id(playerProfile.getId())
        .info(mappedInfo)
        .build();
  }
}
