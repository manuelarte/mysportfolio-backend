package org.manuel.mysportfolio.config.operators;

import org.springframework.stereotype.Component;

@Component
@lombok.EqualsAndHashCode
public class NotEqualQueryOperator implements QueryOperator {

    @Override
    public String getOperator() {
        return "!:";
    }

}
