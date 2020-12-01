package org.manuel.mysportfolio.model.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.NotNull;
import org.manuel.mysportfolio.model.dtos.match.MatchDto;
import org.manuel.mysportfolio.model.dtos.match.PerformanceDto;
import org.manuel.mysportfolio.model.dtos.team.TeamTypeDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Value
@lombok.extern.jackson.Jacksonized @lombok.Builder(toBuilder = true)
public class UserMatchDto {

  @NotNull
  private final MatchDto<TeamTypeDto, TeamTypeDto> match;

  private final PerformanceDto performance;

}
