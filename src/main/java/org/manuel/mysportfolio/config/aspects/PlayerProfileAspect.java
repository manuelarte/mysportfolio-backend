package org.manuel.mysportfolio.config.aspects;

import java.time.Clock;
import java.time.Year;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.entities.player.PlayerProfile;
import org.manuel.mysportfolio.model.entities.player.PlayerProfileSportInfo;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.repositories.PlayerProfileRepository;
import org.manuel.mysportfolio.services.query.AppUserQueryService;
import org.springframework.stereotype.Component;

@Component
@Aspect
@lombok.AllArgsConstructor
public class PlayerProfileAspect {

  private final Clock clock;
  private final AppUserQueryService appUserQueryService;
  private final PlayerProfileRepository playerProfileRepository;

  @Before("execution(* org.manuel.mysportfolio.services.query.impl.PlayerProfileQueryServiceImpl.getByExternalId(..)) && args(externalId,..)")
  public void setPlayerProfileSinceCreationDate(final String externalId) {
    final var playerProfile = playerProfileRepository.findByExternalIdIs(externalId).orElse(PlayerProfile.builder()
        .externalId(externalId)
        .build());
    if (playerProfile.getInfo() == null || playerProfile.getInfo().isEmpty()) {
      final var user = appUserQueryService.findByExternalId(externalId).orElseThrow(() -> new EntityNotFoundException(AppUser.class, externalId));
      // create player profile year entries since the user registered
      final var creationDateYear = Year.from(user.getCreatedDate().get().atZone(ZoneId.systemDefault()));
      final var nowYear = Year.now(clock);
      long between = ChronoUnit.YEARS.between(creationDateYear, nowYear);
      Stream<Year> yearStream = LongStream.range(0, between+1).mapToObj(creationDateYear::plusYears);
      final Map<Year, PlayerProfileSportInfo> info = yearStream.collect(HashMap::new, (m,v)->m.put(v, null), HashMap::putAll);
      playerProfile.setInfo(info);
      this.playerProfileRepository.save(playerProfile);
    }
  }

}
