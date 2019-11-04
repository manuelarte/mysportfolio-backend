package org.manuel.mysportfolio.config.operators;

import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@EqualsAndHashCode
public class LowerThanQueryOperator implements QueryOperator {

    @Override
    public String getOperator() {
        return "<:";
    }

    @Override
    public Function<Criteria, Criteria> addOperation(final Object value) {
        return c -> c.lt(value);
    }

}
