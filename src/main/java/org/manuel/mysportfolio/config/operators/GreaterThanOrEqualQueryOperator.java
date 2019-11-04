package org.manuel.mysportfolio.config.operators;

import org.springframework.stereotype.Component;

@Component
@lombok.EqualsAndHashCode
public class GreaterThanOrEqualQueryOperator implements QueryOperator {

    @Override
    public String getOperator() {
        return ">=:";
    }

}
