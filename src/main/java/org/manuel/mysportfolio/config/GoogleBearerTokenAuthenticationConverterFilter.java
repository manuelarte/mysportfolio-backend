package org.manuel.mysportfolio.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Profile("prod")
public class GoogleBearerTokenAuthenticationConverterFilter implements BearerTokenAuthenticationConverterFilter {

    private final String clientId;

    public GoogleBearerTokenAuthenticationConverterFilter(@Value("${authentication.client-id}") final String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {

        final var transport = new NetHttpTransport();
        final var jacksonFactory = new JacksonFactory();

        final var verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(clientId))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .setAcceptableTimeSkewSeconds(10000000)
                .build();

        final var authorizationHeaderValue = request.getHeader(AUTHORIZATION);
        if (!Strings.isNullOrEmpty(authorizationHeaderValue) && authorizationHeaderValue.contains("Bearer ")) {
            final var idTokenString = request.getHeader(AUTHORIZATION).substring(7);
            try {
                final var idToken = verifier.verify(idTokenString);
                if (idToken != null) {
                    final var payload = idToken.getPayload();

                    // Print user identifier
                    final var userId = payload.getSubject();
                    System.out.println("User ID: " + userId);

                    // Get profile information from payload
                    final var email = payload.getEmail();
                    final var emailVerified = Boolean.valueOf(payload.getEmailVerified());
                    final var name = (String) payload.get("name");
                    final var pictureUrl = (String) payload.get("picture");
                    final var locale = (String) payload.get("locale");
                    final var familyName = (String) payload.get("family_name");
                    final var givenName = (String) payload.get("given_name");

                    // Use or store profile information
                    // ...
                    final var authorities =
                            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
                    final var attributes = new HashMap<String, Object>();
                    attributes.put("sub", userId);
                    attributes.put("email_verified", emailVerified);
                    attributes.put("iss", payload.getIssuer());
                    attributes.put("given_name", givenName);
                    attributes.put("family_name", familyName);
                    attributes.put("name", name);
                    attributes.put("email", email);
                    attributes.put("picture", pictureUrl);

                    final var principal = new DefaultOAuth2User(new HashSet<>(authorities), attributes, "name");

                    final var oAuth2AuthenticationToken =
                        new OAuth2AuthenticationToken(principal, authorities, "clientRegistrationId");

                    SecurityContextHolder.getContext().setAuthentication(oAuth2AuthenticationToken);

                } else {
                    // TODO log that they are trying to access with invalid token
                    System.out.println("Invalid ID token.");
                }

            } catch(final Exception e) {
                // TODO log that they are trying to access with invalid token
                System.out.println("Error when validating token.");
            }
        }

        return true;
    }

}
