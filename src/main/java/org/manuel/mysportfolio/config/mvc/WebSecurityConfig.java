package org.manuel.mysportfolio.config.mvc;

import org.manuel.mysportfolio.config.BearerTokenAuthenticationConverterFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
        .cors().and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/swagger-ui.html").permitAll()
        .antMatchers("/actuator/**").permitAll()
        .anyRequest().authenticated();
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
