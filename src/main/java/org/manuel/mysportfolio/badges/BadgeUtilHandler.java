package org.manuel.mysportfolio.badges;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.type.CompetitionMatchType;
import org.manuel.mysportfolio.model.entities.match.type.FriendlyMatchType;
import org.manuel.mysportfolio.repositories.CompetitionRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class BadgeUtilHandler {

  private final List<Repository<?, ?>> repositories;
  private final UserIdProvider userIdProvider;

  public @Nonnull
  String getUserId() {
    return userIdProvider.getUserId();
  }

  public @Nonnull
  <T extends Repository<?, ?>> Optional<T> getRepository(Class<T> clazz) {
    return (Optional<T>) repositories.stream().filter(clazz::isInstance).findAny();
  }

  public @Nonnull
  Sport getSport(final Match<?, ?> match) {
    final var matchType = match.getType();
    final Sport sport;
    if (matchType instanceof CompetitionMatchType) {
      final var competitionRepository = getRepository(CompetitionRepository.class).get();
      sport = competitionRepository
          .findById(((CompetitionMatchType) matchType).getCompetitionId()).get().getSport();
    } else if (matchType instanceof FriendlyMatchType) {
      sport = ((FriendlyMatchType) matchType).getSport();
    } else {
      throw new RuntimeException(
          "Match type not recognized: " + matchType.getClass().getSimpleName());
    }
    return sport;
  }

}
