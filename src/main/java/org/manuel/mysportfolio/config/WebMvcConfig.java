package org.manuel.mysportfolio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@lombok.AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final BearerTokenAuthenticationConverterFilter googleAuthenticationProviderFilter;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(googleAuthenticationProviderFilter).addPathPatterns("/**");
    }
}
