package org.manuel.mysportfolio.model;

import org.manuel.mysportfolio.config.operators.QueryOperator;

@lombok.AllArgsConstructor
@lombok.Data
public class SearchCriterion<IN, OUT> {

    private final IN key;
    private final QueryOperator<IN, OUT> operation;
    private final OUT value;

    /*
    public Criteria createCriteria() {
        return Criteria.where(key).gt(instant);
    }
     */

}
