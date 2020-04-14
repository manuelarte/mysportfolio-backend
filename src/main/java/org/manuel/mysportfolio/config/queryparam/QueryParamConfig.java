package org.manuel.mysportfolio.config.queryparam;

import io.github.manuelarte.spring.queryparameter.model.TypeTransformerRegistry;
import io.github.manuelarte.spring.queryparameter.mongo.config.QueryParameterConfig;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.springframework.context.annotation.Configuration;

@Configuration
@lombok.AllArgsConstructor
public class QueryParamConfig implements QueryParameterConfig {

  private final QueryParamMatchSportTypeTransformer queryParamMatchSportTypeTransformer;

  @Override
  public void addTypeTransformer(final TypeTransformerRegistry typeTransformerRegistry) {
    typeTransformerRegistry.addTransformer(Match.class, "sport",
        queryParamMatchSportTypeTransformer);
  }
}
