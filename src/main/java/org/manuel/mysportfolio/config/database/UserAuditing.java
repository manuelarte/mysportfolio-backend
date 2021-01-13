package org.manuel.mysportfolio.config.database;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class UserAuditing implements AuditorAware<String> {

  private final UserIdProvider userIdProvider;

  @Override
  public @NotNull Optional<String> getCurrentAuditor() {
    return userIdProvider.getUserId();
  }
}
