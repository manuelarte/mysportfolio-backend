package org.manuel.mysportfolio.transformers.match;

import io.github.manuelarte.mysportfolio.model.documents.match.type.CompetitionMatchType;
import io.github.manuelarte.mysportfolio.model.documents.match.type.FriendlyMatchType;
import io.github.manuelarte.mysportfolio.model.documents.match.type.MatchType;
import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.match.CompetitionMatchTypeDto;
import org.manuel.mysportfolio.model.dtos.match.FriendlyMatchTypeDto;
import org.manuel.mysportfolio.model.dtos.match.MatchTypeDto;
import org.springframework.stereotype.Component;

@Component
public class MatchTypeToMatchTypeDtoTransformer implements Function<MatchType, MatchTypeDto> {

  @Override
  public MatchTypeDto apply(final MatchType matchType) {
    if (matchType instanceof CompetitionMatchType) {
      return CompetitionMatchTypeDto.from((CompetitionMatchType)matchType);
    }
    if (matchType instanceof FriendlyMatchType) {
      return FriendlyMatchTypeDto.from((FriendlyMatchType)matchType);
    }
    return null;
  }
}
