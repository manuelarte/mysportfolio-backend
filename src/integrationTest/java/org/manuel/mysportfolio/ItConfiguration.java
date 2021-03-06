package org.manuel.mysportfolio;

import java.util.Optional;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ItConfiguration {

  public static final String IT_USER_ID = "DefaultUserId";

  @Bean
  @Primary
  public UserIdProvider userIdProvider() {
    return () -> Optional.of(IT_USER_ID);
  }

}
