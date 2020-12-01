package org.manuel.mysportfolio.model.dtos.playerprofile;

import java.time.Year;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import org.bson.types.ObjectId;

@lombok.AllArgsConstructor
@lombok.Value
@lombok.extern.jackson.Jacksonized @lombok.Builder(toBuilder = true)
public class PlayerProfileDto {

  @NotNull
  private final ObjectId id;

  private final Map<
      @PastOrPresent
          Year,
      @Valid
          PlayerProfileSportInfoDto> info;

}
