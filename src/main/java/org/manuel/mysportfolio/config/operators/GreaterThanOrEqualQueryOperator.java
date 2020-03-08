package org.manuel.mysportfolio.config.operators;

import java.util.function.Function;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

@Component
@lombok.EqualsAndHashCode
public class GreaterThanOrEqualQueryOperator implements QueryOperator<String, Object> {

  @Override
  public String getOperator() {
    return ">=:";
  }

  @Override
  public Function<Criteria, Criteria> addOperation(final Object value) {
    return c -> c.gte(value);
  }

  @Override
  public Object getValue(final String value) {
    return value;
  }

}
