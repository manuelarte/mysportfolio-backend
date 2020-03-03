package org.manuel.mysportfolio.config.operators;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

@Component
@lombok.EqualsAndHashCode
public class InQueryOperator implements QueryOperator<String, List<String>> {

  @Override
  public String getOperator() {
    return ":in:";
  }

  @Override
  public Function<Criteria, Criteria> addOperation(final List<String> value) {
    return c -> c.in(value);
  }

  @Override
  public List<String> getValue(String value) {
    return Arrays.asList(value.split(","));
  }

}
