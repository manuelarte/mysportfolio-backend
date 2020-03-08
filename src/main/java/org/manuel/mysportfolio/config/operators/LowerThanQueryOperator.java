package org.manuel.mysportfolio.config.operators;

import java.util.function.Function;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode
public class LowerThanQueryOperator implements QueryOperator<String, Object> {

  @Override
  public String getOperator() {
    return "<:";
  }

  @Override
  public Function<Criteria, Criteria> addOperation(final Object value) {
    return c -> c.lt(value);
  }

  @Override
  public Object getValue(final String value) {
    return value;
  }

}
