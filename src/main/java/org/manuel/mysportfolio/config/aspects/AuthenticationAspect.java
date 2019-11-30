package org.manuel.mysportfolio.config.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.manuel.mysportfolio.model.entities.user.AppMembership;
import org.manuel.mysportfolio.model.entities.user.AppSettings;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.manuel.mysportfolio.services.command.AppUserCommandService;
import org.manuel.mysportfolio.services.query.AppUserQueryService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@Aspect
@lombok.AllArgsConstructor
public class AuthenticationAspect {

    private final AppUserQueryService appUserQueryService;
    private final AppUserCommandService appUserCommandService;

    @After("execution(* org.manuel.mysportfolio.config.FirebaseBearerTokenAuthenticationConverterFilter.doFilter(..))")
    public void afterAuthenticationSet() {
        if (SecurityContextHolder.getContext().getAuthentication() == null ||
                !(SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2AuthenticationToken)) {
            return;
        }
        final var oAuth2AuthenticationToken = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final var attributes = oAuth2AuthenticationToken.getPrincipal().getAttributes();
        final String externalId = (String) attributes.get("sub");
        final String name = (String) attributes.get("name");
        final String email = (String) attributes.get("email");
        final Boolean emailVerified = (Boolean) attributes.get("email_verified");
        final String picture = (String) attributes.get("picture");

        // Use or store profile information
        final AppUser appUser = appUserQueryService.findByExternalId(externalId).orElseGet(
                        () -> AppUser.builder()
                                .fullName(name)
                                .email(email)
                                .externalId(externalId)
                                .appMembership(AppMembership.FREE)
                                .settings(new AppSettings(true))
                                .build());

        if (appUser.isNew()) {
            appUserCommandService.save(appUser);
        }

    }

}
