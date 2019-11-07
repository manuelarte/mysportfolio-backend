package org.manuel.mysportfolio.config;

import com.google.api.client.util.Strings;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.manuel.mysportfolio.model.entities.user.AppMembership;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.model.entities.user.AuthProvider;
import org.manuel.mysportfolio.repositories.AppUserRepository;
import org.manuel.mysportfolio.services.command.AppUserCommandService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Supplier;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Profile("prod")
@lombok.extern.slf4j.Slf4j
public class FirebaseBearerTokenAuthenticationConverterFilter implements BearerTokenAuthenticationConverterFilter {

    private final AppUserRepository appUserRepository;
    private final AppUserCommandService appUserCommandService;

    public FirebaseBearerTokenAuthenticationConverterFilter(
            final AppUserRepository appUserRepository,
            final AppUserCommandService appUserCommandService) throws IOException {
        final var serviceAccount = this.getClass().getClassLoader().getResourceAsStream("./mysportfolio-test-firebase-adminsdk-e6qol-ea622e03c3.json");

        final var options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://mysportfolio-test.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);


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
                final var idToken = FirebaseAuth.getInstance().verifyIdToken(idTokenString);
                final var userId = (String) idToken.getClaims().get("user_id");

                // Get profile information from payload
                final var email = idToken.getEmail();
                final var name = idToken.getName();

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
                    final var attributes = createAttributes(idToken);
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

            } catch(final FirebaseAuthException e) {
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
        attributes.put("email", idToken.getEmail());
        attributes.put("email_verified", idToken.isEmailVerified());
        attributes.put("iss", idToken.getIssuer());
        attributes.put("name", idToken.getName());

        attributes.put("picture", idToken.getPicture());

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
