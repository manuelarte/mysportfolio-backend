package org.manuel.mysportfolio.model.dtos.match;

import io.github.manuelarte.mysportfolio.model.Sport;
import io.github.manuelarte.mysportfolio.model.documents.match.type.FriendlyMatchType;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotNull;

@Immutable
@lombok.Data
@lombok.AllArgsConstructor
@lombok.extern.jackson.Jacksonized @lombok.Builder(toBuilder = true)
public class FriendlyMatchTypeDto implements MatchTypeDto {

  @NotNull
  private final Sport sport;

  public static FriendlyMatchTypeDto from(final FriendlyMatchType matchType) {
    return new FriendlyMatchTypeDto(matchType.getSport());
  }

}
