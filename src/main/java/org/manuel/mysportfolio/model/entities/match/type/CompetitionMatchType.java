package org.manuel.mysportfolio.model.entities.match.type;

import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class CompetitionMatchType implements MatchType {

  @NotNull
  private ObjectId competitionId;

}
