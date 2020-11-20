package org.manuel.mysportfolio.model.dtos.match.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.mysportfolio.model.documents.match.events.HalfTimeMatchEvent;
import java.util.Optional;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.Positive;
import org.bson.types.ObjectId;

@JsonDeserialize(builder = HalfTimeMatchEventDto.HalfTimeMatchEventDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Immutable
@lombok.Data
@lombok.AllArgsConstructor
@lombok.Builder(toBuilder = true)
public class HalfTimeMatchEventDto implements MatchEventDto<HalfTimeMatchEvent> {

  private final String id;

  @Positive
  private final Integer duration;

  @Override
  public HalfTimeMatchEvent toMatchEvent() {
    return new HalfTimeMatchEvent(Optional.ofNullable(id).map(ObjectId::new).orElse(null), duration);
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class HalfTimeMatchEventDtoBuilder {

  }

}
