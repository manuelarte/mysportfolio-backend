package org.manuel.mysportfolio.model.dtos.match.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.mysportfolio.model.documents.match.events.AssistDetails;
import io.github.manuelarte.mysportfolio.model.documents.match.events.BodyPart;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotNull;

@JsonDeserialize(builder = AssistDetailsDto.AssistDetailsDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Immutable
@lombok.Data
@lombok.AllArgsConstructor
@lombok.Builder(toBuilder = true)
public class AssistDetailsDto {

  @NotNull
  private final String who;

  private final BodyPart bodyPart;

  public AssistDetails toAssistDetails() {
    return new AssistDetails(who, bodyPart);
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class AssistDetailsDtoBuilder {

  }

}
