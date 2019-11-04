package org.manuel.mysportfolio.config.operators;

import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode
public class LowerThanQueryOperator implements QueryOperator {

    @Override
    public String getOperator() {
        return "<:";
    }

}
