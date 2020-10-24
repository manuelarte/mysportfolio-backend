package org.manuel.mysportfolio.config.mvc;

import java.util.Arrays;
import java.util.Collections;
import org.manuel.mysportfolio.config.BearerTokenAuthenticationConverterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@lombok.AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final BearerTokenAuthenticationConverterFilter bearerTokenAuthenticationConverterFilter;

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http
        .addFilterBefore(bearerTokenAuthenticationConverterFilter,
            UsernamePasswordAuthenticationFilter.class)
        .csrf().disable()
        .cors().and()
        .authorizeRequests()
        .antMatchers("/swagger-ui.html").permitAll()
        .antMatchers("/actuator/**").permitAll()
        .anyRequest().authenticated();

  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final var configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Collections.singletonList("*"));
    configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    final var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Override
  public void configure(final WebSecurity web) {
    web.ignoring().antMatchers("/v2/api-docs",
        "/configuration/ui",
        "/swagger-resources/**",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**");
  }

}
