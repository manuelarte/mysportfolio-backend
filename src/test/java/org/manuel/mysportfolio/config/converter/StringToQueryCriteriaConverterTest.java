package org.manuel.mysportfolio.config.converter;

import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.config.operators.*;
import org.manuel.mysportfolio.model.QueryCriteria;
import org.manuel.mysportfolio.model.SearchCriterion;
import org.springframework.data.util.Pair;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringToQueryCriteriaConverterTest {

    private static final StringToQueryCriteriaConverter CONVERTER =
            new StringToQueryCriteriaConverter(
                    List.of(new GreaterThanOrEqualQueryOperator(), new GreaterThanQueryOperator(),
                            new LowerThanQueryOperator(), new EqualQueryOperator(), new InQueryOperator()));

    @Test
    public void testOneSingleCriteriaWithLowerThanOperator() {
        final var first = new SearchCriterion("startDate", new LowerThanQueryOperator(), "2001");
        final var expected = new QueryCriteria(first, Collections.emptyList());
        final var source = "startDate<:2001";
        assertEquals(expected, CONVERTER.convert(source));
    }

    @Test
    public void testTwoCriteriaWithLowerAndGreaterThanOperator() {
        final var first = new SearchCriterion("startDate", new LowerThanQueryOperator(), "2001");
        final var second = new SearchCriterion("startDate", new GreaterThanQueryOperator(), "1999");
        final var expected = new QueryCriteria(first, Collections.singletonList(
                Pair.of(QueryCriteria.QueryOption.AND, second)));
        final var source = "startDate<:2001;startDate>:1999";
        assertEquals(expected, CONVERTER.convert(source));
    }

    @Test
    public void testTwoCriteriaWithLowerAndGreaterThanOperatorAndOrInSport() {
        final var first = new SearchCriterion("startDate", new LowerThanQueryOperator(), "2001");
        final var second = new SearchCriterion("startDate", new GreaterThanQueryOperator(), "1999");
        final var third = new SearchCriterion("sport", new EqualQueryOperator(), "FOOTBALL");
        final var expected = new QueryCriteria(first, List.of(
                Pair.of(QueryCriteria.QueryOption.AND, second), Pair.of(QueryCriteria.QueryOption.OR, third)));
        final var source = "startDate<:2001;startDate>:1999|sport::FOOTBALL";
        assertEquals(expected, CONVERTER.convert(source));
    }

    @Test
    public void testIn() {
        final var first = new SearchCriterion("sport", new InQueryOperator(), Arrays.asList("FOOTBALL", "FUTSAL"));
        final var expected = new QueryCriteria(first, Collections.emptyList());
        final var source = "sport:in:FOOTBALL,FUTSAL";
        assertEquals(expected, CONVERTER.convert(source));
    }

}