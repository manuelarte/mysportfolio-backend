package org.manuel.mysportfolio.config;

import java.util.Collections;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SystemAuthentication extends AbstractAuthenticationToken {

  public SystemAuthentication() {
    super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_SYSTEM")));
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return null;
  }
}
