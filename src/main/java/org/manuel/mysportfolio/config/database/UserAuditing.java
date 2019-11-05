package org.manuel.mysportfolio.config.database;

import org.manuel.mysportfolio.config.UserIdProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
