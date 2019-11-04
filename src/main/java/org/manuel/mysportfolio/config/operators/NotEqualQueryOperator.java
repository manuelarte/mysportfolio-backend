package org.manuel.mysportfolio.config.operators;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@lombok.EqualsAndHashCode
public class NotEqualQueryOperator implements QueryOperator {

    @Override
    public String getOperator() {
        return "!:";
    }

    @Override
    public Function<Criteria, Criteria> addOperation(Object value) {
        return c -> c.not().is(value);
    }

}
