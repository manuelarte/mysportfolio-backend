package org.manuel.mysportfolio.model.dtos.playerprofile;

import java.time.Month;
import java.util.Map;

public interface SeasonSummaryDto {

  Map<Month, PlayerProfileTimeIntervalSummaryDto> getSummary();

}
