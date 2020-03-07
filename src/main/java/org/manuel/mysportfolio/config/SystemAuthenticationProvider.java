package org.manuel.mysportfolio.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class SystemAuthenticationProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    return new SystemAuthentication();
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(SystemAuthentication.class);
  }

}
