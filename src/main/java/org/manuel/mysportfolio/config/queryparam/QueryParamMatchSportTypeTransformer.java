package org.manuel.mysportfolio.config.queryparam;

import io.github.manuelarte.spring.queryparameter.transformers.TypeTransformer;
import org.manuel.mysportfolio.model.Sport;
import org.springframework.stereotype.Component;

/**
 * We need this type transformer because we want to use it with the 'sport' field name,
 * that does not directly map with a field in Match.class
 */
@Component
public class QueryParamMatchSportTypeTransformer implements TypeTransformer<String, Sport> {

  @Override
  public Sport transformValue(final Class<?> clazz, final String key, final String value) {
    return Sport.valueOf(value);
  }
}
