package org.manuel.mysportfolio.config.database;

import java.util.Optional;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class UserAuditing implements AuditorAware<String> {

  private final UserIdProvider userIdProvider;

  @Override
  public Optional<String> getCurrentAuditor() {
    // get your user name here
    return Optional.ofNullable(userIdProvider.getUserId());
  }
}
