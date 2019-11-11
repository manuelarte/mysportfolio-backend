package org.manuel.mysportfolio.config.converter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.manuel.mysportfolio.config.operators.QueryOperator;
import org.manuel.mysportfolio.model.QueryCriteria;
import org.manuel.mysportfolio.model.SearchCriterion;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@lombok.AllArgsConstructor
public class StringToQueryCriteriaConverter implements Converter<String, QueryCriteria> {

    private final List<QueryOperator> queryOperators;

    @Override
    public QueryCriteria convert(final String source) {
        String temp = source;
        final List<DataHelper> test1 = new ArrayList<>();
        while(!temp.isEmpty()) {
            final var dataHelper = convertToDataHelper(temp);
            test1.add(dataHelper);
            temp = dataHelper.rest;
        }

        final var first = test1.get(0).searchCriterion;
        var rest = test1.subList(1, test1.size()).stream().map(dH -> Pair.of(dH.queryOption, dH.searchCriterion)).collect(Collectors.toList());
        return new QueryCriteria(first, rest);
    }

    private QueryCriteria.QueryOption getQueryOption(final char theChar) {
        final QueryCriteria.QueryOption toReturn;
        switch(theChar) {
            case '|':
                toReturn = QueryCriteria.QueryOption.OR;
                break;
            default:
                toReturn = QueryCriteria.QueryOption.AND;
                break;
        }
        return toReturn;
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


    private DataHelper convertToDataHelper(final String string) {
        StringBuilder key = new StringBuilder();
        QueryOperator operator = null;
        StringBuilder value = new StringBuilder();

        DataHelper dataHelper = new DataHelper();
        int index = 0;
        if (isSeparator(string.charAt(index))) {
            dataHelper.queryOption = getQueryOption(string.charAt(index));
            index = index + 1;
        }
        for(int i = index; i < string.length(); i++) {
            if (isOperator(string.charAt(i)) && getOperator(string.substring(i)).isPresent()) {
                key.append(string, index, i);
                operator = getOperator(string.substring(i)).get();
                final var to = i + operator.getOperator().length();
                index = to;
                i = index;
            }

            if (isSeparator(string.charAt(i))) {
                value.append(string, index, i);
                index = i;
                break;
            }
        }

        // if no separator everything is a value
        if (value.length() == 0) {
            value.append(string, index, string.length());
            index = string.length();
        }
        dataHelper.rest = string.substring(index);


        if (key.length() == 0 || operator == null || value.length() == 0) {
            throw new RuntimeException(String.format("Query param %s malformed", string));
        }
        dataHelper.searchCriterion = new SearchCriterion<>(key.toString(), operator, value.toString());
        return dataHelper;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    private class DataHelper {
        public SearchCriterion<String> searchCriterion;
        public QueryCriteria.QueryOption queryOption;
        public String rest;
    }
}
