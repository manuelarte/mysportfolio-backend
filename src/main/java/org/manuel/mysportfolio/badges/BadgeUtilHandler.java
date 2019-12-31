package org.manuel.mysportfolio.badges;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import org.manuel.mysportfolio.config.UserIdProvider;
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

}
