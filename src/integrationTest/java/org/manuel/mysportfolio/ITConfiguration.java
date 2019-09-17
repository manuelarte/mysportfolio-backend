package org.manuel.mysportfolio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class ITConfiguration {

    @Bean
    @Primary
    public AuditorAware<String> userAuditing() {
        return () -> Optional.of("test");
    }
}
