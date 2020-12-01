package org.manuel.mysportfolio.model.dtos.playerprofile;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.Null;

@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.EqualsAndHashCode
@lombok.Data
@lombok.extern.jackson.Jacksonized @lombok.Builder(toBuilder = true)
public class PlayerProfileTimeIntervalSummaryDto {

  @Null
  private final Integer numberOfMatchesPlayed;

  @Null
  private final Integer numberOfGoals;

  @Null
  private final Integer numberOfAssists;

  // TODO Max performance, average performance, lower performance

}
