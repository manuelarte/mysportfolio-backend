package org.manuel.mysportfolio.model.dtos.match.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.mysportfolio.model.TeamOption;
import io.github.manuelarte.mysportfolio.model.documents.match.events.SubstitutionMatchEvent;
import java.util.Optional;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@JsonDeserialize(builder = SubstitutionMatchEventDto.SubstitutionMatchEventDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Immutable
@lombok.Data
@lombok.AllArgsConstructor
@lombok.Builder(toBuilder = true)
public class SubstitutionMatchEventDto implements MatchEventDto<SubstitutionMatchEvent> {

  @Id
  private final String id;

  @NotNull
  private final TeamOption team;

  private final Integer minute;

  private final String in;

  private final String out;

  @Size(max = 200)
  private final String description;

  @Override
  public SubstitutionMatchEvent toMatchEvent() {
    return new SubstitutionMatchEvent(Optional.ofNullable(id).map(ObjectId::new).orElse(null), team, minute, in, out, description);
  }

  @AssertTrue
  private boolean isInOrOutSet() {
    return in != null || out != null;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class SubstitutionMatchEventDtoBuilder {

  }

}
