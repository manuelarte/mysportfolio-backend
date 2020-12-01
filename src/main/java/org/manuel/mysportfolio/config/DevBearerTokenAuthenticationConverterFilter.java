package org.manuel.mysportfolio.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.github.manuelarte.mysportfolio.model.documents.user.AppMembership;
import io.github.manuelarte.mysportfolio.model.documents.user.AppUser;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.manuel.mysportfolio.model.auth.AuthPrincipal;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
@lombok.AllArgsConstructor
class DevBearerTokenAuthenticationConverterFilter implements
    BearerTokenAuthenticationConverterFilter {

  private static final String DEFAULT_USER_ID = "DefaultUserId";

  private final ObjectMapper objectMapper;

  @Override
  public void init(FilterConfig filterConfig) {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    final var httpRequest = (HttpServletRequest) request;
    final var mockUserHeaderValue = httpRequest.getHeader("x-mock-user");

    final var authorities =
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

    final AuthPrincipal principal;
    if (!Strings.isNullOrEmpty(mockUserHeaderValue)) {
      final Map<String, Object> mockUserValue = createClaims(objectMapper.readValue(httpRequest.getHeader("x-mock-user"), HashMap.class));
      principal = new AuthPrincipal(authorities, mockUserValue, (AppMembership) mockUserValue.get("app-membership"));
    } else {
      principal = new AuthPrincipal(authorities, createClaims(Collections.emptyMap()), AppMembership.NOOB);
    }
    final var oAuth2AuthenticationToken =
        new OAuth2AuthenticationToken(principal, authorities, "clientRegistrationId");
    SecurityContextHolder.getContext().setAuthentication(oAuth2AuthenticationToken);
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
  }

  private Map<String, Object> createClaims(Map<String, Object> mockUser) {
    final var attributes = new HashMap<String, Object>();
    // NO-COMMIT
    attributes.put("uid", mockUser.getOrDefault("uid", DEFAULT_USER_ID));
    attributes.put("sub", mockUser.getOrDefault("sub", DEFAULT_USER_ID));
    attributes.put("name", mockUser.getOrDefault("name", "Test User"));
    attributes.put("email", mockUser.getOrDefault("email", "mysportfolio@mysportfolio.org"));
    attributes.put("picture",
        "https://lh3.googleusercontent.com/a-/AAuE7mBk0hY2RSA_JMUDFNo2wT54GjycNKMGgtLfw5X1LpQ=s96-c");
    attributes.put("app-membership", mockUser.getOrDefault("app-membership", AppMembership.NOOB));
    return attributes;
  }

  private AppUser createUser(final Map<String, Object> claims) {
    return AppUser.builder()
        .externalId((String)claims.getOrDefault("sub", DEFAULT_USER_ID))
        .fullName((String)claims.getOrDefault("name", "Test User"))
        .appMembership((AppMembership) claims.getOrDefault("app-membership", AppMembership.NOOB))
        .email((String)claims.getOrDefault("email", "mysportfolio@mysportfolio.org"))
        .admin((Boolean)claims.getOrDefault("admin", false))
        .build();
  }

}
