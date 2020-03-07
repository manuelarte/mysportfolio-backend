package org.manuel.mysportfolio.config;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.google.api.client.util.Strings;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.manuel.mysportfolio.model.entities.user.AppMembership;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
@lombok.extern.slf4j.Slf4j
public class FirebaseBearerTokenAuthenticationConverterFilter implements
    BearerTokenAuthenticationConverterFilter {

  private final AppUserRepository appUserRepository;

  public FirebaseBearerTokenAuthenticationConverterFilter(
      @Value("${FIREBASE_ADMIN_SDK}") final String json,
      final AppUserRepository appUserRepository) throws IOException {

    final var serviceAccount = new ByteArrayInputStream(json.getBytes());
    final var options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("https://mysportfolio-test.firebaseio.com")
        .build();
    FirebaseApp.initializeApp(options);
    this.appUserRepository = appUserRepository;
  }

  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    final var httpRequest = (HttpServletRequest) request;
    final var authorizationHeaderValue = httpRequest.getHeader(AUTHORIZATION);

    if (!Strings.isNullOrEmpty(authorizationHeaderValue) && authorizationHeaderValue
        .contains("Bearer ")) {
      final var idTokenString = httpRequest.getHeader(AUTHORIZATION).substring(7);
      try {
        final var idToken = FirebaseAuth.getInstance().verifyIdToken(idTokenString);
        final var userId = (String) idToken.getClaims().get("user_id");

        // Get profile information from payload
        final var email = idToken.getEmail();
        final var name = idToken.getName();

        // Use or store profile information
        final Optional<AppUser> appUser = doWithSystemAuthentication(
            () -> appUserRepository.findByExternalId(userId));

        final var authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (Boolean.TRUE.equals(appUser.map(AppUser::getAdmin).orElse(false))) {
          authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        final var attributes = createAttributes(idToken);
        // custom attributes
        // check if I can receive this from firebase claims
        attributes.put("app-membership",
            appUser.map(AppUser::getAppMembership).orElse(AppMembership.FREE));

        final var principal = new DefaultOAuth2User(new HashSet<>(authorities), attributes, "name");
        final var oAuth2AuthenticationToken =
            new OAuth2AuthenticationToken(principal, authorities, "clientRegistrationId");
        SecurityContextHolder.getContext().setAuthentication(oAuth2AuthenticationToken);
      } catch (final FirebaseAuthException e) {
        log.info("Error when validating firebase token.", e);
      }
    }
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {

  }

  private Map<String, Object> createAttributes(final FirebaseToken idToken) {

    // Get profile information from payload
    // final var locale = (String) payload.get("locale");

    final var attributes = new HashMap<String, Object>();
    attributes.put("sub", idToken.getClaims().get("sub"));
    attributes.put("name", idToken.getName());
    attributes.put("email", idToken.getEmail());
    attributes.put("email_verified", idToken.isEmailVerified());
    attributes.put("iss", idToken.getIssuer());
    attributes.put("picture", idToken.getPicture());

    return attributes;
  }

  private <T> T doWithSystemAuthentication(final Supplier<T> action) {
    try {
      SecurityContextHolder.getContext().setAuthentication(new SystemAuthentication());
      return action.get();
    } finally {
      SecurityContextHolder.clearContext();
    }
  }


}
