package org.manuel.mysportfolio.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.manuel.mysportfolio.config.serializer.PointDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.geo.Point;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        final var module = new SimpleModule();
        module.addDeserializer(Point.class, new PointDeserializer());
        return new ObjectMapper().findAndRegisterModules()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .registerModule(module);
    }
}
