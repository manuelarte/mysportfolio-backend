package org.manuel.mysportfolio.transformers;

import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.config.operators.GreaterThanQueryOperator;
import org.manuel.mysportfolio.config.operators.LowerThanQueryOperator;
import org.manuel.mysportfolio.model.QueryCriteria;
import org.manuel.mysportfolio.model.SearchCriterion;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.services.TypeConversionService;
import org.springframework.data.util.Pair;

import java.util.Collections;

class QueryCriteriaToMongoQueryTransformerTest {
    
    private static final QueryCriteriaToMongoQueryTransformer QUERY_CRITERIA_TO_MONGO_QUERY_TRANSFORMER = 
            new QueryCriteriaToMongoQueryTransformer(new TypeConversionService());
    
    @Test
    public void test() {
        final var first = new SearchCriterion<>("startDate",
                new GreaterThanQueryOperator(), "2019-10-20T14:12:04.537881500Z");

        final var second = new SearchCriterion<>("startDate",
                new LowerThanQueryOperator(), "2019-11-04T14:12:04.537881500Z");


        final QueryCriteria queryCriteria = new QueryCriteria(new SearchCriterion<>("startDate", 
                new GreaterThanQueryOperator(), "2019-11-04T14:12:04.537881500Z"),
                Collections.singletonList(Pair.of(QueryCriteria.QueryOption.AND, second)));

        final var query = QUERY_CRITERIA_TO_MONGO_QUERY_TRANSFORMER.apply(queryCriteria, Match.class);
    }

}