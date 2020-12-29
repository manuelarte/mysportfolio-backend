package org.manuel.mysportfolio.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.time.Clock;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.config.serializer.ObjectIdModule;
import org.manuel.mysportfolio.config.serializer.PointDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.geo.Point;

@Configuration
public class AppConfig {

  @Bean
  public Clock clock() {
    return Clock.systemUTC();
  }

  @Bean
  public ObjectMapper objectMapper() {
    final var objectIdModule = new SimpleModule("ObjectIdModule");
    objectIdModule.addSerializer(ObjectId.class, new ObjectIdModule.ObjectIdSerializer());
    objectIdModule.addDeserializer(ObjectId.class, new ObjectIdModule.ObjectIdDeserializer());

    final var module = new SimpleModule();
    module.addDeserializer(Point.class, new PointDeserializer());
    return new ObjectMapper().findAndRegisterModules()
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .registerModule(module)
        .registerModule(objectIdModule);
  }

}
