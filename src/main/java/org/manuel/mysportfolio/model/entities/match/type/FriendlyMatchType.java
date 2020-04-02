package org.manuel.mysportfolio.model.entities.match.type;

import javax.validation.constraints.NotNull;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportDependent;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class FriendlyMatchType implements MatchType, SportDependent {

  @NotNull
  private Sport sport;

}
