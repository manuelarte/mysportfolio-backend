package org.manuel.mysportfolio.model;

import org.manuel.mysportfolio.config.operators.QueryOperator;

@lombok.AllArgsConstructor
@lombok.Data
public class SearchCriterion<T> {

    private final String key;
    private final QueryOperator operation;
    private final T value;

}
