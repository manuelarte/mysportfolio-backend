package org.manuel.mysportfolio.config.converter;

import org.manuel.mysportfolio.config.operators.QueryOperator;
import org.manuel.mysportfolio.model.QueryCriteria;
import org.manuel.mysportfolio.model.SearchCriterion;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO, add AND OR support
@Component
@lombok.AllArgsConstructor
public class StringToQueryCriteriaConverter implements Converter<String, QueryCriteria> {

    private final List<QueryOperator> queryOperators;

    @Override
    public QueryCriteria convert(final String source) {

        final var split = source.split("[;|]");

        final List<SearchCriterion<String>> collect = Arrays.stream(split).map(s -> convertSearchCriterion(s)).collect(Collectors.toList());
        final var searchCriterion = collect.get(0);
        final List<Pair<QueryCriteria.QueryOption, SearchCriterion<String>>> rest;
        if (collect.size() > 1) {
            rest = collect.subList(1, collect.size()).stream().map(s -> Pair.of(QueryCriteria.QueryOption.AND, s))
                    .collect(Collectors.toList());
        } else {
            rest = Collections.emptyList();
        }
        return new QueryCriteria(searchCriterion, rest);
    }

    private SearchCriterion<String> convertSearchCriterion(final String string) {
        StringBuilder key = new StringBuilder();
        QueryOperator operator = null;
        StringBuilder value = new StringBuilder();
        int index = 0;
        for(int i = 0; i < string.length(); i++) {
            if (isOperator(string.charAt(i)) && getOperator(string.substring(i)).isPresent()) {
                key.append(string, index, i);
                operator = getOperator(string.substring(i)).get();
                final var to = i + operator.getOperator().length();
                index = to;

                value.append(string, index, string.length());
                break;
            }
        }

        if (key.length() == 0 || operator == null || value.length() == 0) {
            throw new RuntimeException(String.format("Query param %s malformed", string));
        }
        return new SearchCriterion(key.toString(), operator, value.toString());
    }

    private boolean isOperator(final char c) {
        return queryOperators.stream().filter(q -> q.getOperator().charAt(0) == c).findAny().isPresent();
    }

    private Optional<QueryOperator> getOperator(final String string) {
        return queryOperators.stream()
                .filter(q -> q.getOperator().equals(string.substring(0, q.getOperator().length())))
                .findFirst();
    }

    private boolean isSeparator(final char c) {
        return c == ';' || c == '|';
    }
}
