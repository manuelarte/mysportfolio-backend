package org.manuel.mysportfolio.config.operators;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.function.Function;

public interface QueryOperator<IN, OUT> {

    String getOperator();

    Function<Criteria, Criteria> addOperation(OUT value);

    OUT getValue(IN value);

}
