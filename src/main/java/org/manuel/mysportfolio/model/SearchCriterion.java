package org.manuel.mysportfolio.model;

import org.manuel.mysportfolio.config.operators.QueryOperator;
import org.springframework.data.mongodb.core.query.Criteria;

@lombok.AllArgsConstructor
@lombok.Data
public class SearchCriterion<T> {

    private final String key;
    private final QueryOperator operation;
    private final T value;

    /*
    public Criteria createCriteria() {
        return Criteria.where(key).gt(instant);
    }
     */

}
