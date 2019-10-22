package org.manuel.mysportfolio;

import org.manuel.mysportfolio.config.UserIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ITConfiguration {

    @Bean
    @Primary
    public UserIdProvider userIdProvider() {
        return () -> "integrationTest";
    }

}
