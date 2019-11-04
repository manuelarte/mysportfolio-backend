package org.manuel.mysportfolio.transformers;

import org.manuel.mysportfolio.model.QueryCriteria;
import org.manuel.mysportfolio.model.SearchCriterion;
import org.manuel.mysportfolio.services.TypeConversionService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Component
@lombok.AllArgsConstructor
public class QueryCriteriaToMongoQueryTransformer implements BiFunction<QueryCriteria, Class<?>, Query> {

    private final TypeConversionService typeConversionService;

    @Override
    public Query apply(final QueryCriteria queryCriteria, final Class<?> clazz) {
        final Query query = new Query();

        final var criteria = createCriteria(queryCriteria.getFirst(), clazz);

        final List<Pair<QueryCriteria.QueryOption, Criteria>> restCriteria = queryCriteria.getRest().stream().map(p -> Pair.of(p.getFirst(), createCriteria(p.getSecond(), clazz)) )
                .collect(Collectors.toList());

        for(Pair<QueryCriteria.QueryOption, Criteria> p: restCriteria) {
            if (p.getFirst() == QueryCriteria.QueryOption.AND) {
                criteria.andOperator(p.getSecond());
            } else if (p.getFirst() == QueryCriteria.QueryOption.OR) {
                criteria.orOperator(p.getSecond());
            }
        }
        query.addCriteria(criteria);
        return query;
    }

    private <T> Criteria createCriteria(final SearchCriterion searchCriterion, final Class<T> clazz) {
        final var criteria = Criteria.where(searchCriterion.getKey());
        try {
            final Field field = clazz.getDeclaredField(searchCriterion.getKey());
            final Object converted = typeConversionService.convert(searchCriterion.getValue(), field.getType());
            searchCriterion.getOperation().addOperation(converted).apply(criteria);
        } catch (final NoSuchFieldException e) {
            throw new RuntimeException(String.format("Field %s not found in class %s", searchCriterion.getKey(), clazz.getSimpleName()));
        }
        return criteria;
    }

}
