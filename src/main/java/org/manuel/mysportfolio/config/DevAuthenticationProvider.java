package org.manuel.mysportfolio.config;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Profile("dev")
@lombok.AllArgsConstructor
public class DevAuthenticationProvider implements AuthenticationProvider {

    private final Random random = new Random();

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

        final var authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        // attributes
        final var attributes = new HashMap<String, Object>();
        attributes.put("sub", random.nextLong());
        attributes.put("email_verified", true);
        attributes.put("iss", "https://accounts.google.com");
        attributes.put("given_name", "Test");
        attributes.put("family_name", "Not Production");
        attributes.put("name", "Test Not Production");
        attributes.put("email", "test@test.com");
        attributes.put("picture", "https://lh3.googleusercontent.com/a-/AAuE7mBk0hY2RSA_JMUDFNo2wT54GjycNKMGgtLfw5X1LpQ=s96-c");
        //aud=[318145913734-n58mtpcgt54rq5ni4rb4bpaf3huf5tfq.apps.googleusercontent.com]

        final var principal = new DefaultOAuth2User(new HashSet<>(authorities), attributes, "name");

        final var oAuth2AuthenticationToken =
                new OAuth2AuthenticationToken(principal, authorities, "clientRegistrationId");

        return oAuth2AuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
