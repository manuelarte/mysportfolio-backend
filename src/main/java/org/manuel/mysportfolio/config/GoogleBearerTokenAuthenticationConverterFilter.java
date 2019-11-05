package org.manuel.mysportfolio.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Strings;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.model.entities.user.AuthProvider;
import org.manuel.mysportfolio.model.entities.user.AppMembership;
import org.manuel.mysportfolio.repositories.AppUserRepository;
import org.manuel.mysportfolio.services.command.AppUserCommandService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Profile("prod")
@lombok.extern.slf4j.Slf4j
public class GoogleBearerTokenAuthenticationConverterFilter implements BearerTokenAuthenticationConverterFilter {

    private final GoogleIdTokenVerifier verifier;
    private final AppUserRepository appUserRepository;
    private final AppUserCommandService appUserCommandService;

    public GoogleBearerTokenAuthenticationConverterFilter(
            @Value("${authentication.client-id}") final String clientId,
            final AppUserRepository appUserRepository,
            final AppUserCommandService appUserCommandService) {
        final var transport = new NetHttpTransport();
        final var jacksonFactory = new JacksonFactory();
        verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(clientId))
                .setAcceptableTimeSkewSeconds(10000000)
                .build();

        this.appUserRepository = appUserRepository;
        this.appUserCommandService = appUserCommandService;
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final var httpRequest = (HttpServletRequest) request;
        final var authorizationHeaderValue = httpRequest.getHeader(AUTHORIZATION);
        if (!Strings.isNullOrEmpty(authorizationHeaderValue) && authorizationHeaderValue.contains("Bearer ")) {
            final var idTokenString = httpRequest.getHeader(AUTHORIZATION).substring(7);
            try {
                final var idToken = verifier.verify(idTokenString);
                if (idToken != null) {
                    final var payload = idToken.getPayload();

                    // Print user identifier
                    final var userId = payload.getSubject();

                    // Get profile information from payload
                    final var email = payload.getEmail();
                    final var name = (String) payload.get("name");

                    // Use or store profile information
                    final AppUser appUser = doWithSystemAuthentication(
                            () -> appUserRepository.findByExternalId(userId).orElseGet(
                                    () -> AppUser.builder()
                                            .fullName(name)
                                            .email(email)
                                            .authProvider(AuthProvider.GOOGLE)
                                            .externalId(userId)
                                            .appMembership(AppMembership.FREE)
                                            .build())
                    );


                    final var authorities = new ArrayList<GrantedAuthority>();
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                    if (Boolean.TRUE.equals(appUser.getAdmin())) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    }
                    final var attributes = createAttributes(payload);
                    // custom attributes
                    attributes.put("app-membership", appUser.getAppMembership());

                    final var principal = new DefaultOAuth2User(new HashSet<>(authorities), attributes, "name");
                    final var oAuth2AuthenticationToken =
                            new OAuth2AuthenticationToken(principal, authorities, "clientRegistrationId");
                    SecurityContextHolder.getContext().setAuthentication(oAuth2AuthenticationToken);

                    // TODO check if do it in aspect
                    if (appUser.isNew()) {
                        appUserCommandService.save(appUser);
                    }
                } else {
                    log.info("Invalid ID token");
                }

            } catch(final Exception e) {
                log.info("Error when validating token.");
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private Map<String, Object> createAttributes(final GoogleIdToken.Payload payload) {

        // Get profile information from payload
        // final var locale = (String) payload.get("locale");

        final var attributes = new HashMap<String, Object>();
        attributes.put("sub", payload.getSubject());
        attributes.put("email", payload.getEmail());
        attributes.put("email_verified", Boolean.valueOf(payload.getEmailVerified()));
        attributes.put("iss", payload.getIssuer());
        attributes.put("given_name", payload.get("given_name"));
        attributes.put("family_name", payload.get("family_name"));
        attributes.put("name", payload.get("name"));

        attributes.put("picture", payload.get("picture"));

        return attributes;
    }

    private AppUser doWithSystemAuthentication(final Supplier<AppUser> action) {
        try {
            SecurityContextHolder.getContext().setAuthentication(new SystemAuthentication());
            return action.get();
        } finally {
            SecurityContextHolder.clearContext();
        }
    }


}
