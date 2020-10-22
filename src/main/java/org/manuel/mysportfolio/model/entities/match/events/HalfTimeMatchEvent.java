package org.manuel.mysportfolio.model.entities.match.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import javax.validation.constraints.Positive;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class HalfTimeMatchEvent implements MatchEvent {

  @Positive
  private Integer duration;

  @Id
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId id;

}
