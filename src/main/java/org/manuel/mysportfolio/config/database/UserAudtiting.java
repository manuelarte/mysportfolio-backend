package org.manuel.mysportfolio.config.database;

import org.manuel.mysportfolio.config.UserIdProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@lombok.AllArgsConstructor
public class UserAudtiting implements AuditorAware<String> {

    private final UserIdProvider userIdProvider;

    @Override
    public Optional<String> getCurrentAuditor() {
        // get your user name here
        return Optional.ofNullable(userIdProvider.getUserId());
    }
}
