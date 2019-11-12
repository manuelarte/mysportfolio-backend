package org.manuel.mysportfolio.transformers;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.manuel.mysportfolio.model.QueryCriteria;
import org.manuel.mysportfolio.model.SearchCriterion;
import org.manuel.mysportfolio.services.TypeConversionService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@lombok.AllArgsConstructor
public class QueryCriteriaToMongoQueryTransformer implements BiFunction<QueryCriteria, Class<?>, Query> {

    private final TypeConversionService typeConversionService;

    @Override
    public Query apply(final QueryCriteria queryCriteria, final Class<?> clazz) {
        final var groupByQueryOption = groupByQueryOption(queryCriteria);
        final var criteria = new Criteria();
        final Map<QueryCriteria.QueryOption, List<Criteria>> mapped = groupByQueryOption.asMap().keySet().stream().collect(Collectors.toMap(Function.identity(),
                    x -> groupByQueryOption.get(x).stream().map(s -> createCriteria(s, clazz)).collect(Collectors.toList())));

        mapped.keySet().forEach( qO -> {
            final var inArray = mapped.get(qO).toArray(new Criteria[mapped.get(qO).size()]);
            switch (qO) {
                case AND:
                    criteria.andOperator(inArray);
                    break;
                case OR:
                    criteria.orOperator(inArray);
                    break;
            }
        });

        return new Query(criteria);
    }

    private Multimap<QueryCriteria.QueryOption, SearchCriterion> groupByQueryOption(final QueryCriteria queryCriteria) {
        final Multimap<QueryCriteria.QueryOption, SearchCriterion> groupByQueryOption = ArrayListMultimap.create();
        groupByQueryOption.put(QueryCriteria.QueryOption.AND, queryCriteria.getFirst());

        queryCriteria.getRest().forEach(qC -> groupByQueryOption.put(qC.getFirst(), qC.getSecond()));
        return groupByQueryOption;
    }

    private <T> Criteria createCriteria(final SearchCriterion<String, Object> searchCriterion, final Class<T> clazz) {
        final var criteria = Criteria.where(searchCriterion.getKey());
        try {
            final Field field = clazz.getDeclaredField(searchCriterion.getKey());
            final Object converted;
            if (searchCriterion.getValue() instanceof Iterable) {
                converted = typeConversionService.convertToList((Iterable)searchCriterion.getValue(), field.getType());
            } else {
                converted = typeConversionService.convert(searchCriterion.getValue(), field.getType());
            }
            searchCriterion.getOperation().addOperation(converted).apply(criteria);
        } catch (final NoSuchFieldException e) {
            throw new RuntimeException(String.format("Field %s not found in class %s", searchCriterion.getKey(), clazz.getSimpleName()));
        }
        return criteria;
    }

}
