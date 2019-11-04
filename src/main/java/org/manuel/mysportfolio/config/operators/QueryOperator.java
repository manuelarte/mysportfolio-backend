package org.manuel.mysportfolio.config.operators;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.function.Function;

public interface QueryOperator {

    String getOperator();

    Function<Criteria, Criteria> addOperation(Object value);

}
