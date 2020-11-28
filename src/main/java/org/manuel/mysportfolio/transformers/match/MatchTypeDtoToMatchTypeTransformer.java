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
public class MatchTypeDtoToMatchTypeTransformer implements Function<MatchTypeDto, MatchType> {

  @Override
  public MatchType apply(final MatchTypeDto matchTypeDto) {
    if (matchTypeDto instanceof CompetitionMatchTypeDto) {
      final var casted = (CompetitionMatchTypeDto) matchTypeDto;
      return new CompetitionMatchType(casted.getCompetitionId());
    }
    if (matchTypeDto instanceof FriendlyMatchTypeDto) {
      final var casted = (FriendlyMatchTypeDto) matchTypeDto;
      return new FriendlyMatchType(casted.getSport());
    }
    return null;
  }
}
