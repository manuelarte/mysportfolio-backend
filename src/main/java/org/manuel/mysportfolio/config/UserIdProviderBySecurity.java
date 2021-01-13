package org.manuel.mysportfolio.config;

import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class UserIdProviderBySecurity implements UserIdProvider {

  @Override
  public Optional<String> getUserId() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication()).map(Authentication::getPrincipal)
        .filter(it -> it instanceof OAuth2User)
        .map(it -> (OAuth2User)it).map(it -> it.getAttributes().get("sub").toString());
  }
}
