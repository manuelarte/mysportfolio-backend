package org.manuel.mysportfolio.config.queryparam;

import io.github.manuelarte.spring.queryparameter.transformers.TypeTransformer;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class QueryParamLocalDateTypeTransformer implements TypeTransformer<String, LocalDate> {

  @Override
  public LocalDate transformValue(final Class<?> clazz, final String key, final String value) {
    // try here the null hack
    if ("(null)".equals(value)) {
      return null;
    }
    return LocalDate.parse(value);
  }

}
