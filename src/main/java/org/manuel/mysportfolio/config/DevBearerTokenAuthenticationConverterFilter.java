package org.manuel.mysportfolio.config;

import org.manuel.mysportfolio.model.entities.user.AppMembership;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import javax.servlet.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

@Component
@Profile("!prod")
@lombok.AllArgsConstructor
class DevBearerTokenAuthenticationConverterFilter implements BearerTokenAuthenticationConverterFilter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        final var authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        // attributes
        final var attributes = new HashMap<String, Object>();
        // NO-COMMIT
        attributes.put("sub", "123456789");
        attributes.put("email_verified", true);
        attributes.put("iss", "https://accounts.google.com");
        attributes.put("given_name", "Test");
        attributes.put("family_name", "Not Production");
        attributes.put("name", "Test Not Production");
        attributes.put("email", "test@test.com");
        attributes.put("picture", "https://lh3.googleusercontent.com/a-/AAuE7mBk0hY2RSA_JMUDFNo2wT54GjycNKMGgtLfw5X1LpQ=s96-c");
        //aud=[318145913734-n58mtpcgt54rq5ni4rb4bpaf3huf5tfq.apps.googleusercontent.com]
        attributes.put("app-membership", AppMembership.FREE);

        final var principal = new DefaultOAuth2User(new HashSet<>(authorities), attributes, "name");

        final var oAuth2AuthenticationToken =
                new OAuth2AuthenticationToken(principal, authorities, "clientRegistrationId");

        SecurityContextHolder.getContext().setAuthentication(oAuth2AuthenticationToken);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
