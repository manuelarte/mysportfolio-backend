package org.manuel.mysportfolio.model;

import org.springframework.data.util.Pair;

import java.util.List;

@lombok.AllArgsConstructor
@lombok.Data
@lombok.EqualsAndHashCode
@lombok.Builder(toBuilder = true)
public class QueryCriteria {

    public enum QueryOption { AND, OR }

    private final SearchCriterion<String> first;

    @lombok.Singular("addAnotherCriteria")
    private final List<Pair<QueryOption, SearchCriterion<String>>> rest;

}
