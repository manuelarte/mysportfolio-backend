package org.manuel.mysportfolio.model.dtos.team;

import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Immutable;

/**
 * Dto to be used if the home/away team is a registered team when user inputs a match.
 */
@JsonTypeName("registered")
@Immutable
@lombok.AllArgsConstructor
@lombok.Value
@lombok.extern.jackson.Jacksonized @lombok.Builder(toBuilder = true)
public class RegisteredTeamDto implements TeamTypeDto {

  @NotNull
  private final ObjectId teamId;

}
