package org.manuel.mysportfolio.config.database;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAudtiting implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // get your user name here
        final var principal = (DefaultOAuth2User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(principal.getAttributes().get("sub").toString());
    }
}
