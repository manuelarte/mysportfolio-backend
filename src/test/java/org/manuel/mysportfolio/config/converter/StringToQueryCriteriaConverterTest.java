package org.manuel.mysportfolio.config.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.config.operators.EqualQueryOperator;
import org.manuel.mysportfolio.config.operators.GreaterThanOrEqualQueryOperator;
import org.manuel.mysportfolio.config.operators.GreaterThanQueryOperator;
import org.manuel.mysportfolio.config.operators.InQueryOperator;
import org.manuel.mysportfolio.config.operators.LowerThanQueryOperator;
import org.manuel.mysportfolio.model.QueryCriteria;
import org.manuel.mysportfolio.model.SearchCriterion;
import org.springframework.data.util.Pair;

class StringToQueryCriteriaConverterTest {

  private static final StringToQueryCriteriaConverter CONVERTER =
      new StringToQueryCriteriaConverter(
          List.of(new GreaterThanOrEqualQueryOperator(), new GreaterThanQueryOperator(),
              new LowerThanQueryOperator(), new EqualQueryOperator(), new InQueryOperator()));

  @Test
  public void testOneSingleCriteriaWithLowerThanOperator() {
    final SearchCriterion first = new SearchCriterion("startDate", new LowerThanQueryOperator(),
        "2001");
    final QueryCriteria expected = new QueryCriteria(first, Collections.emptyList());
    final String source = "startDate<:2001";
    assertEquals(expected, CONVERTER.convert(source));
  }

  @Test
  public void testTwoCriteriaWithLowerAndGreaterThanOperator() {
    final SearchCriterion first = new SearchCriterion("startDate", new LowerThanQueryOperator(),
        "2001");
    final SearchCriterion second = new SearchCriterion("startDate", new GreaterThanQueryOperator(),
        "1999");
    final QueryCriteria expected = new QueryCriteria(first, Collections.singletonList(
        Pair.of(QueryCriteria.QueryOption.AND, second)));
    final String source = "startDate<:2001;startDate>:1999";
    assertEquals(expected, CONVERTER.convert(source));
  }

  @Test
  public void testTwoCriteriaWithLowerAndGreaterThanOperatorAndOrInSport() {
    final SearchCriterion first = new SearchCriterion("startDate", new LowerThanQueryOperator(),
        "2001");
    final SearchCriterion second = new SearchCriterion("startDate", new GreaterThanQueryOperator(),
        "1999");
    final SearchCriterion third = new SearchCriterion("sport", new EqualQueryOperator(),
        "FOOTBALL");
    final QueryCriteria expected = new QueryCriteria(first, List.of(
        Pair.of(QueryCriteria.QueryOption.AND, second),
        Pair.of(QueryCriteria.QueryOption.OR, third)));
    final String source = "startDate<:2001;startDate>:1999|sport::FOOTBALL";
    assertEquals(expected, CONVERTER.convert(source));
  }

  @Test
  public void testIn() {
    final SearchCriterion first = new SearchCriterion("sport", new InQueryOperator(),
        Arrays.asList("FOOTBALL", "FUTSAL"));
    final QueryCriteria expected = new QueryCriteria(first, Collections.emptyList());
    final String source = "sport:in:FOOTBALL,FUTSAL";
    assertEquals(expected, CONVERTER.convert(source));
  }

}