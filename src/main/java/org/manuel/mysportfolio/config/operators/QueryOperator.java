package org.manuel.mysportfolio.config.operators;

import java.util.function.Function;
import org.springframework.data.mongodb.core.query.Criteria;

public interface QueryOperator<IN, OUT> {

  String getOperator();

  Function<Criteria, Criteria> addOperation(OUT value);

  OUT getValue(IN value);

}
