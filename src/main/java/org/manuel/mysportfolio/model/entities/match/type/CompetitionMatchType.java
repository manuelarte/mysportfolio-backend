package org.manuel.mysportfolio.model.entities.match.type;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class CompetitionMatchType implements MatchType {

  @NotNull
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId competitionId;

}
