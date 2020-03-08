package org.manuel.mysportfolio.config.operators;

import java.util.function.Function;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode
public class LowerThanOrEqualQueryOperator implements QueryOperator<String, Object> {

  @Override
  public String getOperator() {
    return "<=:";
  }

  @Override
  public Function<Criteria, Criteria> addOperation(final Object value) {
    return c -> c.lte(value);
  }

  @Override
  public Object getValue(final String value) {
    return value;
  }

}
