package org.manuel.mysportfolio;

import org.manuel.mysportfolio.config.UserIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ItConfiguration {

  public static final String IT_USER_ID = "123456789";

  @Bean
  @Primary
  public UserIdProvider userIdProvider() {
    return () -> IT_USER_ID;
  }

}
