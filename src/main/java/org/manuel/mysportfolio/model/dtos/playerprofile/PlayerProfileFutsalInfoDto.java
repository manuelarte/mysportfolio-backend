package org.manuel.mysportfolio.model.dtos.playerprofile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.mysportfolio.model.documents.player.FootballSkills;
import io.github.manuelarte.mysportfolio.model.documents.player.FutsalPosition;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.New;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.PartialUpdate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.Update;
import java.util.Set;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = PlayerProfileFutsalInfoDto.PlayerProfileFutsalInfoDtoBuilder.class)
@lombok.AllArgsConstructor
@lombok.Data
@lombok.Builder(toBuilder = true)
public class PlayerProfileFutsalInfoDto {

  @Null(groups = {New.class, Update.class, PartialUpdate.class})
  private final PlayerProfileSportSeasonSummaryDto summary;

  private final FutsalPosition preferredPosition;
  @Size(max = 3)
  private final Set<FutsalPosition> alternativePositions;
  private final FootballSkills skills;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class PlayerProfileFutsalInfoDtoBuilder {

  }

}
