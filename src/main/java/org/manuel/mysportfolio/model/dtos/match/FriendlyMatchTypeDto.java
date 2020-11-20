package org.manuel.mysportfolio.model.dtos.match;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.mysportfolio.model.Sport;
import io.github.manuelarte.mysportfolio.model.documents.match.type.FriendlyMatchType;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotNull;

@JsonDeserialize(builder = FriendlyMatchTypeDto.FriendlyMatchTypeDtoBuilder.class)
@Immutable
@lombok.Data
@lombok.AllArgsConstructor
@lombok.Builder(toBuilder = true)
public class FriendlyMatchTypeDto implements MatchTypeDto {

  @NotNull
  private final Sport sport;

  public static FriendlyMatchTypeDto from(final FriendlyMatchType matchType) {
    return new FriendlyMatchTypeDto(matchType.getSport());
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class FriendlyMatchTypeDtoBuilder {

  }
}
