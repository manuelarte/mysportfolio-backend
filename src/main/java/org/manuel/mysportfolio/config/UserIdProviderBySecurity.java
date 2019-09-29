package org.manuel.mysportfolio.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

@Component
public class UserIdProviderBySecurity implements UserIdProvider{

    @Override
    public String getUserId() {
        return ((DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getAttributes().get("sub").toString();
    }
}
