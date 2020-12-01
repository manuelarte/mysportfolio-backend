package org.manuel.mysportfolio.model.dtos.playerprofile;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.manuelarte.mysportfolio.model.documents.player.FootballPosition;
import io.github.manuelarte.mysportfolio.model.documents.player.FootballSkills;
import java.time.Month;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.AllArgsConstructor
@lombok.Data
@lombok.extern.jackson.Jacksonized @lombok.Builder(toBuilder = true)
public class PlayerProfileFootballInfoDto implements SeasonSummaryDto {

  @Null
  private final Map<Month, PlayerProfileTimeIntervalSummaryDto> summary;

  private final FootballPosition preferredPosition;
  @Size(max = 3)
  private final Set<FootballPosition> alternativePositions;
  private final FootballSkills skills;

}
