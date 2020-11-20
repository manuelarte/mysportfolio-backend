package org.manuel.mysportfolio.config.queryparam;

import io.github.manuelarte.mysportfolio.model.documents.match.Match;
import io.github.manuelarte.mysportfolio.model.documents.teamtouser.UserInTeam;
import io.github.manuelarte.spring.queryparameter.config.QueryParameterConfig;
import io.github.manuelarte.spring.queryparameter.model.TypeTransformerRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
@lombok.AllArgsConstructor
public class QueryParamConfig implements QueryParameterConfig {

  private final QueryParamMatchSportTypeTransformer queryParamMatchSportTypeTransformer;
  private final QueryParamLocalDateTypeTransformer queryParamLocalDateTypeTransformer;

  @Override
  public void addTypeTransformer(final TypeTransformerRegistry typeTransformerRegistry) {
    typeTransformerRegistry.addTransformer(Match.class, "sport",
        queryParamMatchSportTypeTransformer);
    typeTransformerRegistry.addTransformer(UserInTeam.class, "to", queryParamLocalDateTypeTransformer);
  }
}
