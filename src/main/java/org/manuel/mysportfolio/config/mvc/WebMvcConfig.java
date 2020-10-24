package org.manuel.mysportfolio.config.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableWebMvc
class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("*")
        .allowedMethods("HEAD", "GET", "PUT", "POST", "OPTION", "DELETE", "PATCH");;
  }

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(new LocaleChangeInterceptor());
  }
}
