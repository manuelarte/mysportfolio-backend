package org.manuel.mysportfolio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@lombok.AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BearerTokenAuthenticationConverterFilter bearerTokenAuthenticationConverterFilter;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .addFilterBefore(bearerTokenAuthenticationConverterFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated();
    }

}
