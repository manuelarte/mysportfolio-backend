package org.manuel.mysportfolio.config.operators;

import java.util.function.Function;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

@Component
@lombok.EqualsAndHashCode
public class NotEqualQueryOperator implements QueryOperator<String, Object> {

  @Override
  public String getOperator() {
    return "!:";
  }

  @Override
  public Function<Criteria, Criteria> addOperation(Object value) {
    return c -> c.not().is(value);
  }

  @Override
  public Object getValue(final String value) {
    return value;
  }

}
