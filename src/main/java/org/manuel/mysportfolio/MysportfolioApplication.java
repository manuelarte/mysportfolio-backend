package org.manuel.mysportfolio;

import io.github.manuelarte.mysportfolio.exceptions.config.ControllerExceptionAdvice;
import io.github.manuelarte.spring.queryparameter.mongo.EnableQueryParameter;
import org.manuel.mysportfolio.config.properties.UserRestrictionsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(UserRestrictionsConfig.class)
@EnableAspectJAutoProxy
@EnableScheduling
@EnableQueryParameter
@Import(ControllerExceptionAdvice.class)
public class MysportfolioApplication {

  public static void main(String[] args) {
    SpringApplication.run(MysportfolioApplication.class, args);
  }

}
