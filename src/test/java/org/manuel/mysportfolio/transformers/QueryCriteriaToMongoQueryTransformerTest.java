package org.manuel.mysportfolio.transformers;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.config.operators.GreaterThanQueryOperator;
import org.manuel.mysportfolio.config.operators.LowerThanQueryOperator;
import org.manuel.mysportfolio.model.QueryCriteria;
import org.manuel.mysportfolio.model.SearchCriterion;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.services.TypeConversionService;
import org.springframework.data.util.Pair;

class QueryCriteriaToMongoQueryTransformerTest {

  private static final QueryCriteriaToMongoQueryTransformer
      QUERY_CRITERIA_TO_MONGO_QUERY_TRANSFORMER =
      new QueryCriteriaToMongoQueryTransformer(new TypeConversionService());

  @Test
  public void testSameFieldOnlyOneAndConditionIsCreated() {
    final var first = new SearchCriterion<>("startDate",
        new GreaterThanQueryOperator(), "2019-10-20");

    final var second = new SearchCriterion<>("startDate",
        new LowerThanQueryOperator(), "2019-11-04");

    final QueryCriteria queryCriteria = new QueryCriteria(first,
        Collections.singletonList(Pair.of(QueryCriteria.QueryOption.AND, second)));

    final var query = QUERY_CRITERIA_TO_MONGO_QUERY_TRANSFORMER.apply(queryCriteria, Match.class);
  }

  @Test
  public void testTwoKeysAllAndOneAndIsCreated() {
    final var first = new SearchCriterion<>("startDate",
        new GreaterThanQueryOperator(), "2019-10-20");

    final var second = new SearchCriterion<>("startDate",
        new LowerThanQueryOperator(), "2019-11-04");

    final QueryCriteria queryCriteria = new QueryCriteria(first,
        List.of(Pair.of(QueryCriteria.QueryOption.AND, second)));

    final var query = QUERY_CRITERIA_TO_MONGO_QUERY_TRANSFORMER.apply(queryCriteria, Match.class);
  }

  @Test
  public void testTwoKeysAndAndOr() {
    final var first = new SearchCriterion<>("startDate",
        new GreaterThanQueryOperator(), "2019-10-20");

    final var second = new SearchCriterion<>("startDate",
        new LowerThanQueryOperator(), "2019-11-04");

    final QueryCriteria queryCriteria = new QueryCriteria(first,
        List.of(Pair.of(QueryCriteria.QueryOption.OR, second)));

    final var query = QUERY_CRITERIA_TO_MONGO_QUERY_TRANSFORMER.apply(queryCriteria, Match.class);
  }

  @Test
  public void testTwoKeysAndAndIn() {
    final var first = new SearchCriterion<>("startDate",
        new GreaterThanQueryOperator(), "2019-10-20");

    final var second = new SearchCriterion<>("startDate",
        new LowerThanQueryOperator(), "2019-11-04");

    final QueryCriteria queryCriteria = new QueryCriteria(first,
        List.of(Pair.of(QueryCriteria.QueryOption.AND, second)));

    final var query = QUERY_CRITERIA_TO_MONGO_QUERY_TRANSFORMER.apply(queryCriteria, Match.class);
  }

}