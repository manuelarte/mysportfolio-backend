package org.manuel.mysportfolio.config.database;

import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

  @Bean
  public ValidatingMongoEventListener validatingMongoEventListener() {
    return new ValidatingMongoEventListener(validator());
  }

  @Bean
  public LocalValidatorFactoryBean validator() {
    return new LocalValidatorFactoryBean();
  }

  @Bean
  public MongoCustomConversions customConversions() {
    final List<Converter<?, ?>> converterList = new ArrayList<>();
    converterList.add(new MongoYearFromStringConverter());
    converterList.add(new MongoYearMonthFromStringConverter());
    converterList.add(new MongoStringFromYearMonthConverter());
    return new MongoCustomConversions(converterList);
  }

  private static final class MongoYearFromStringConverter implements Converter<String, Year> {

    @Override
    public Year convert(final String source) {
      return Year.parse(source);
    }
  }

  private static final class MongoYearMonthFromStringConverter implements
      Converter<String, YearMonth> {

    @Override
    public YearMonth convert(final String source) {
      return YearMonth.parse(source);
    }
  }

  private static final class MongoStringFromYearMonthConverter implements
      Converter<YearMonth, String> {

    @Override
    public String convert(final YearMonth source) {
      return source.toString();
    }
  }

}
